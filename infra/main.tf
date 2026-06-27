provider "aws" {
  region = var.region
}

# -----------------------------
# Get Infra Project Remote State
# -----------------------------
data "terraform_remote_state" "infra" {
  backend = "s3"
  config = {
    bucket = "${var.state_bucket_name}"
    key    = "infra/terraform.tfstate"
    region = var.region
  }
}

locals {
  image_tag = "1.1.3"  # update this each release
  ecr_repository_url = data.terraform_remote_state.infra.outputs.repository_url
  ecs_cluster_name   = data.terraform_remote_state.infra.outputs.ecs_cluster_name
  ecs_task_execution_role_arn = data.terraform_remote_state.infra.outputs.ecs_task_execution_role_arn
  public_subnet_ids  = data.terraform_remote_state.infra.outputs.public_subnet_ids
  ecs_security_group_ids = [data.terraform_remote_state.infra.outputs.ecs_cluster_sg_id]
  vpc_id = data.terraform_remote_state.infra.outputs.vpc_id
  alb_arn = data.terraform_remote_state.infra.outputs.alb_arn
  aws_lb_listener = data.terraform_remote_state.infra.outputs.https_listener_arn
}


# Target group
resource "aws_lb_target_group" "common_service_tg" {
  name        = "${var.project}-${var.env}-common-service-tg"
  port        = 8081
  protocol    = "HTTP"
  vpc_id      = local.vpc_id
  target_type = "ip"

  health_check {
    path                = "/health/ready"
    interval            = 180
    timeout             = 60
    healthy_threshold   = 2
    unhealthy_threshold = 5
    matcher             = "200"
    protocol            = "HTTP"
  }
}

resource "aws_ecs_task_definition" "common_service" {
  family                   = "${var.project}-${var.env}-common-service"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = "512"
  memory                   = "1024"
  execution_role_arn       = local.ecs_task_execution_role_arn

  container_definitions = jsonencode([
    {
      name  = "common-service"
      image = "${local.ecr_repository_url}:${var.image_tag}"
      portMappings = [
        {
          containerPort = 8081
          hostPort      = 8081
        }
      ]
      
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = "ecs-log-group"
          awslogs-region        = var.region
          awslogs-stream-prefix = "common-service"
        }
      }
    }
  ])
}

resource "aws_ecs_service" "common_service" {
  name            = "${var.project}-${var.env}-common-service"
  cluster         = local.ecs_cluster_name
  task_definition = aws_ecs_task_definition.common_service.arn
  launch_type     = "FARGATE"
  desired_count   = 1

  network_configuration {
    subnets          = local.public_subnet_ids
    security_groups  = local.ecs_security_group_ids
    assign_public_ip = true
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.common_service_tg.arn
    container_name   = "common-service"
    container_port   = 8081
  }

    # 👇 This ensures Common Service waits until DB is marked created
  depends_on = [
    aws_lb_target_group.common_service_tg
  ]
}

resource "aws_lb_listener_rule" "common_service_path_rule" {
  listener_arn = local.aws_lb_listener
  priority     = 10

  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.common_service_tg.arn
  }

  condition {
    host_header {
      values = ["auth-${var.env}.${var.domain_name}"] 
    }
  }
}