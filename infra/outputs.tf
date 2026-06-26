output "keycloak_task_definition_arn" {
  description = "ARN of the Keycloak ECS task definition"
  value       = aws_ecs_task_definition.keycloak.arn
}

output "keycloak_service_name" {
  description = "Name of the Keycloak ECS service"
  value       = aws_ecs_service.keycloak.name
}

output "keycloak_service_id" {
  description = "ID of the Keycloak ECS service"
  value       = aws_ecs_service.keycloak.id
}
