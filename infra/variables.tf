variable "region" {
  type    = string
  default = "ap-south-2"
}

variable "state_bucket_name" {
  description = "S3 bucket name for Terraform state storage"
  type        = string
}

variable "service_name" {
  type = string
  description = "Name of the service; used for naming resources"
  default = "agrifarm-common-service"
}

variable "image_tag" {
  type        = string
  description = "ECR image tag for the container (e.g., 'latest' or a SHA)"
  default     = "latest"
}

variable "container_port" {
  type    = number
  default = 8080
}

variable "desired_count" {
  type    = number
  default = 2
}

variable "env" {
  type    = string
  default = "prod"
}
