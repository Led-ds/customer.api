terraform {
	backend "s3" {
		bucket = "builders-terraform-state"
		key = "builders-api-customer-app"
		region = "us-east-1"
		profile = "terraform"
	}
}