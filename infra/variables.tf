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
variable "db_username" {
  description = "Database username for Keycloak"
  type        = string
}
variable "db_password" {
  description = "Database password for Keycloak"
  type        = string
  sensitive   = true
}
variable "db_name" {
  description = "Database name for Keycloak"
  type        = string
}
variable "db_url" {
  description = "Database URL for Keycloak (without port and db name)"
  type        = string
}
variable "db_port" {
  description = "Database port for Keycloak"
  type        = string
}
variable "db_schema" {
  description = "Database schema for Keycloak"
  type        = string
}
variable "admin_user_name" {
  description = "Admin username for Keycloak"
  type        = string
}
variable "admin_user_password" {
  description = "Admin password for Keycloak"
  type        = string
  sensitive   = true
}
variable "image_tag" {
  type = string
}