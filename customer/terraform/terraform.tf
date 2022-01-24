terraform {
	backend "s3" { #Foi criado um usuário na aws para o terraform atuar no provisionamento
		bucket = "builders-terraform-state"
		key = "builders-api-customer-app"
		region = "us-east-1"
		profile = "terraform"
	}
}