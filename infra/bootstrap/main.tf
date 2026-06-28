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
  vpc_id = data.terraform_remote_state.infra.outputs.vpc_id
  public_subnet_ids  = data.terraform_remote_state.infra.outputs.public_subnet_ids
  ecs_cluster_name   = data.terraform_remote_state.infra.outputs.ecs_cluster_name
  aws_lb_listener = data.terraform_remote_state.infra.outputs.https_listener_arn
}


resource "aws_ecr_repository" "ecr" {
  name                 = "${var.project}-${var.env}-common-service-repo"
  image_tag_mutability = "IMMUTABLE"
  force_delete         = true

  image_scanning_configuration {
    scan_on_push = true
  }

  encryption_configuration {
    encryption_type = "AES256"
  }

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

resource "aws_lb_listener_rule" "common_service_path_rule" {
  listener_arn = local.aws_lb_listener
  priority     = 10

  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.common_service_tg.arn
  }

  condition {
    host_header {
      values = ["common-${var.env}.${var.domain_name}"] 
    }
  }
}