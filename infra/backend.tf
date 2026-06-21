terraform {
  backend "s3" {
    bucket = "agri-prod-terraform-s3-state"
    # NOTE: replace <service-name> below or run `terraform init -backend-config="key=services/<your-service>/terraform.tfstate"`
    key    = "infra/common-service/terraform.tfstate"
    region = "ap-south-2"
    encrypt = true
  }
}
