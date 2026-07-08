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

data "terraform_remote_state" "service_infra" {
  backend = "s3"

  config = {
    bucket = var.state_bucket_name
    key    = "infra/common-service/infra.tfstate"
    region = var.region
  }
}

data "aws_ecr_image" "latest" {
  repository_name = "${var.ecr_repository_name}"
  most_recent     = true
}

locals {
  ecs_cluster_name   = data.terraform_remote_state.infra.outputs.ecs_cluster_name
  public_subnet_ids  = data.terraform_remote_state.infra.outputs.public_subnet_ids
  ecs_security_group_ids = [data.terraform_remote_state.infra.outputs.ecs_cluster_sg_id]
  vpc_id = data.terraform_remote_state.infra.outputs.vpc_id
  alb_arn = data.terraform_remote_state.infra.outputs.alb_arn
  aws_lb_listener = data.terraform_remote_state.infra.outputs.https_listener_arn
  ecs_task_execution_role_arn = data.terraform_remote_state.infra.outputs.ecs_task_execution_role_arn
  ecr_repository_url = data.terraform_remote_state.service_infra.outputs.ecr_repository_url
  common_service_tg_arn = data.terraform_remote_state.service_infra.outputs.common_service_tg_arn
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
      image = "${local.ecr_repository_url}:${data.aws_ecr_image.latest.image_tags[0]}"
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
    target_group_arn = local.common_service_tg_arn
    container_name   = "common-service"
    container_port   = 8081
  }
}