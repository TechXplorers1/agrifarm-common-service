output "service_name" {
  description = "ECS service name"
  value       = aws_ecs_service.svc.name
}

output "target_group_arn" {
  description = "ALB target group ARN"
  value       = aws_lb_target_group.svc_tg.arn
}

output "listener_rule_arn" {
  description = "ALB listener rule ARN"
  value       = aws_lb_listener_rule.svc_rule.arn
}
