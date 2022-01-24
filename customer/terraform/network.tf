# Provisionando VPC
resource "aws_vpc" "main" {
  cidr_block = "192.168.0.0/16" # CIDR MAIN
  tags = {
    Name = "Builders@VPC"
  }
}

# Provisionando SubNet Privada
resource "aws_subnet" "private_subnet" { #Provisionando SUB_VPC PRIVATE By Main
  count = 3
  
  vpc_id = "${aws_vpc.main.id}"
  
  cidr_block = "${cidrsubnet(aws_vpc.main.cidr_block, 8, count.index + 10)}" #Ouput example:  192.168.10.0/24 in the first iteration
  
  availability_zone = "${var.availability_zones[count.index]}" #Zonas de Disponibilidades

  tags = { Name = "builders_vpc_private_subnet_${count.index}"}
}

# Provisionando SubNet Publica
resource "aws_subnet" "public_subnet" { #Provisionando SUB_VPC PUBLIC By Main
  count = 3

  vpc_id = "${aws_vpc.main.id}"

  cidr_block = "${cidrsubnet(aws_vpc.main.cidr_block, 8, count.index + 20)}"

  availability_zone = "${var.availability_zones[count.index]}" #Zonas de Disponibilidades

  map_public_ip_on_launch = true

  tags = { Name = "builders_vpc_public_subnet_${count.index}"}
}

#Declarando uma Gateway para acesso as instancias via SSH
resource "aws_internet_gateway" "gateway" {
  vpc_id = "${aws_vpc.main.id}"
}

#Para qualquer endereço publico que não seja uma especifico das sub redes use o internet gateway
resource "aws_route_table" "route_gateway" {
  vpc_id = "${aws_vpc.main.id}"

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = "${aws_internet_gateway.gateway.id}"
  }
}

#Associando as Rotas com os Ip das Sub redes publicas
resource "aws_route_table_association" "route_gateway_association" {
  count = 3

  route_table_id = "${aws_route_table.route_gateway.id}"

  subnet_id = "${element(aws_subnet.public_subnet.*.id, count.index)}"
}