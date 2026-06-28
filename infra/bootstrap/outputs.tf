output "ecr_repository_url" {
  value = aws_ecr_repository.ecr.repository_url
}

output "ecr_repository_name" {
  value = aws_ecr_repository.ecr.name
}

output "ecs_task_execution_role_arn" {
  value = aws_iam_role.ecs_task_execution_role.arn
}

output "common_service_tg_arn" {
  value = aws_lb_target_group.common_service_tg.arn
}