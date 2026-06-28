variable "region" {
  description = "The region to create resources."
  type = string
}

variable "project" {
  description = "Project name for tagging resources"
  type        = string
}

variable "state_bucket_name" {
  description = "S3 bucket name for Terraform state storage"
  type        = string
}

variable "env" {
  description = "Project environment (e.g., dev, staging, prod) for tagging resources"
  type        = string
}

variable "domain_name" {
  description = "The domain name for the application (e.g., example.com)"
  type        = string
}

variable "ecr_repository_name" {
  description = "The name of the ECR repository"
  type        = string
}