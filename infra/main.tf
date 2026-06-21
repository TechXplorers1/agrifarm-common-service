provider "aws" {
  region = var.region
}

// Read infra remote state
data "terraform_remote_state" "infra" {
  backend = "s3"
  config = {
    bucket = "agri-prod-terraform-s3-state"
    key    = "infra/common-server/terraform.tfstate"
    region = var.region
  }
}

// Security group for the service tasks
resource "aws_security_group" "svc_sg" {
  name   = "${var.service_name}-sg"
  vpc_id = data.terraform_remote_state.infra.outputs.vpc_id

  ingress {
    description = "Allow from ALB SG"
    from_port   = var.container_port
    to_port     = var.container_port
    protocol    = "tcp"
    security_groups = [
      // ALB SG unknown from remote outputs; try to derive from listener ARN? user must ensure alb_security_group_id is exposed
    ]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

// CloudWatch Log Group
resource "aws_cloudwatch_log_group" "svc_logs" {
  name              = "/ecs/${var.service_name}"
  retention_in_days = 14
}

// IAM roles
resource "aws_iam_role" "task_execution_role" {
  name = "${var.service_name}-execution-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_task_execution_assume_role_policy.json
}

data "aws_iam_policy_document" "ecs_task_execution_assume_role_policy" {
  statement {
    actions = ["sts:AssumeRole"]
    principals {
      type = "Service"
      identifiers = ["ecs-tasks.amazonaws.com"]
    }
  }
}

resource "aws_iam_role_policy_attachment" "execution_attach" {
  role       = aws_iam_role.task_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

resource "aws_iam_role" "task_role" {
  name = "${var.service_name}-task-role"
  assume_role_policy = data.aws_iam_policy_document.ecs_task_role_assume.json
}

data "aws_iam_policy_document" "ecs_task_role_assume" {
  statement {
    actions = ["sts:AssumeRole"]
    principals {
      type = "Service"
      identifiers = ["ecs-tasks.amazonaws.com"]
    }
  }
}

resource "aws_iam_role_policy" "task_role_policy" {
  name = "${var.service_name}-task-role-policy"
  role = aws_iam_role.task_role.id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "logs:CreateLogStream",
          "logs:PutLogEvents"
        ]
        Resource = "*"
      }
    ]
  })
}

// Task definition
resource "aws_ecs_task_definition" "task" {
  family                   = var.service_name
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = "512"
  memory                   = "1024"
  execution_role_arn       = aws_iam_role.task_execution_role.arn
  task_role_arn            = aws_iam_role.task_role.arn

  container_definitions = jsonencode([
    {
      name      = var.service_name
      image     = "${data.aws_ecr_repository.repo.repository_url}:${var.image_tag}"
      essential = true
      portMappings = [
        {
          containerPort = var.container_port
          protocol = "tcp"
        }
      ]
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = aws_cloudwatch_log_group.svc_logs.name
          awslogs-region        = var.region
          awslogs-stream-prefix = var.service_name
        }
      }
    }
  ])
}

// ECR data
data "aws_ecr_repository" "repo" {
  name = var.service_name
}

// ECS service
resource "aws_ecs_service" "svc" {
  name            = var.service_name
  cluster         = data.terraform_remote_state.infra.outputs.ecs_cluster_arn
  task_definition = aws_ecs_task_definition.task.arn
  desired_count   = var.desired_count
  launch_type     = "FARGATE"

  network_configuration {
    subnets         = data.terraform_remote_state.infra.outputs.private_subnet_ids
    security_groups = [aws_security_group.svc_sg.id]
    assign_public_ip = false
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.svc_tg.arn
    container_name   = var.service_name
    container_port   = var.container_port
  }

  depends_on = [aws_lb_listener_rule.svc_rule]
}

// Target group
resource "aws_lb_target_group" "svc_tg" {
  name     = "${var.service_name}-tg"
  port     = var.container_port
  protocol = "HTTP"
  vpc_id   = data.terraform_remote_state.infra.outputs.vpc_id
  target_type = "ip"
  health_check {
    path = "/"
    matcher = "200-399"
  }
}

// Listener rule
resource "aws_lb_listener_rule" "svc_rule" {
  listener_arn = data.terraform_remote_state.infra.outputs.alb_listener_arn
  priority     = 100

  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.svc_tg.arn
  }

  condition {
    path_pattern {
      values = ["/${var.service_name}/*"]
    }
  }
}

// Autoscaling
resource "aws_appautoscaling_target" "ecs_scaling_target" {
  max_capacity = 4
  min_capacity = 1
  resource_id  = "service/${replace(data.terraform_remote_state.infra.outputs.ecs_cluster_name, ":", "/")}/${aws_ecs_service.svc.name}"
  scalable_dimension = "ecs:service:DesiredCount"
  service_namespace  = "ecs"
}

resource "aws_appautoscaling_policy" "cpu_policy" {
  name               = "${var.service_name}-cpu-policy"
  policy_type        = "TargetTrackingScaling"
  resource_id        = aws_appautoscaling_target.ecs_scaling_target.resource_id
  scalable_dimension = aws_appautoscaling_target.ecs_scaling_target.scalable_dimension
  service_namespace  = aws_appautoscaling_target.ecs_scaling_target.service_namespace

  target_tracking_scaling_policy_configuration {
    predefined_metric_specification {
      predefined_metric_type = "ECSServiceAverageCPUUtilization"
    }
    target_value = 60.0
  }
}

// Outputs
output "service_name" {
  value = aws_ecs_service.svc.name
}

output "service_url_path" {
  value = "https://${data.terraform_remote_state.infra.outputs.alb_arn.split(":")[5]}/${var.service_name}/"
}
