output "ecr_repository_url" {
  value = aws_ecr_repository.ecr.repository_url
}

output "ecr_repository_name" {
  value = aws_ecr_repository.ecr.name
}

output "common_service_tg_arn" {
  value = aws_lb_target_group.common_service_tg.arn
}