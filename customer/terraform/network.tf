resource "aws_vpc" "main" {     #Provisionando VPC
  cidr_block = "192.168.0.0/16"
  tags = {
    Name = "Builders@VPC"
  }
}

resource "aws_subnet" "private_subnet" { #Provisionando SUB_VPC PRIVATE By Main
  count = 3
  
  vpc_id = "${aws_vpc.main.id}"
  
  cidr_block = "${cidrsubnet(aws_vpc.main.cidr_block, 8, count.index + 10)}" #Ouput example:  192.168.10.0/24 in the first iteration
  
  availability_zone = "${var.availability_zones[count.index]}" #Zonas de Disponibilidades

  tags = { Name = "builders_vpc_private_subnet_${count.index}"}
}

resource "aws_subnet" "public_subnet" { #Provisionando SUB_VPC PUBLIC By Main
  count = 3

  vpc_id = "${aws_vpc.main.id}"

  cidr_block = "${cidrsubnet(aws_vpc.main.cidr_block, 8, count.index + 20)}"

  availability_zone = "${var.availability_zones[count.index]}" #Zonas de Disponibilidades

  map_public_ip_on_launch = true

  tags = { Name = "builders_vpc_public_subnet_${count.index}"}
}

resource "aws_internet_gateway" "gateway" {
  vpc_id = "${aws_vpc.main.id}"
}

resource "aws_route_table" "route_gateway" {
  vpc_id = "${aws_vpc.main.id}"

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = "${aws_internet_gateway.gateway.id}"
  }
}

resource "aws_route_table_association" "route_gateway_association" {
  count = 3

  route_table_id = "${aws_route_table.route_gateway.id}"

  subnet_id = "${element(aws_subnet.public_subnet.*.id, count.index)}"
}