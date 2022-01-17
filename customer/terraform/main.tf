provider "aws" {
  version = "~> 3.72"
  shared_credentials_file = "~/.aws/credentials"
  profile = "terraform"
  region = "us-east-1"
}