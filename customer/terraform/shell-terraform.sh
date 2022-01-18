#!/bin/bash
echo "Provisioning infrastructure"

echo "Finding by public ip address..."
MY_PUBLIC_IP="$(curl -s ipinfo.io/ip)"

echo "Starting terraform"
terraform apply -var "my_public_ip=$MY_PUBLIC_IP/32"