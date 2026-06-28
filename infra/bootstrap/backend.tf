terraform {
  backend "s3" {
    bucket = "agri-prod-terraform-s3-state"            # name of S3 bucket
    key    = "infra/common-service/infra.tfstate"      # Change this if you want to target a different existing file
    region         = "ap-south-2"                      # "ap-south-2"
    encrypt        = true                              # Ensure this matches your AWS region
  }
}