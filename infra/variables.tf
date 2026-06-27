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

variable "ssl_policy" {
  description = "SSL policy for the HTTPS listener"
  type        = string
  default     = "ELBSecurityPolicy-2016-08"
}

variable "domain_name" {
  description = "The domain name for the application (e.g., example.com)"
  type        = string
}

variable "image_tag" {
  type = string
}