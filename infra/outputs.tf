output "common_service_task_definition_arn" {
  description = "ARN of the Common Service ECS task definition"
  value       = aws_ecs_task_definition.common_service.arn
}

output "common_service_name" {
  description = "Name of the Common Service ECS service"
  value       = aws_ecs_service.common_service.name
}

output "common_service_id" {
  description = "ID of the Common Service ECS service"
  value       = aws_ecs_service.common_service.id
}
