resource "aws_security_group" "allow_ssh" {
  vpc_id = aws_vpc.main.id
  name = "builders_allow_ssh"

  ingress {
    from_port = 22
    to_port   = 22
    protocol  = "tcp"
    cidr_blocks = ["${var.my_public_ip}"]
  }
}

resource "aws_security_group" "database" {
  vpc_id = aws_vpc.main.id
  name = "builders_database"

  ingress {
    from_port = 5432
    protocol  = "tcp"
    to_port   = 5432
    self = true
  }
}

resource "aws_security_group" "allow_outbound" {
  vpc_id = "${aws_vpc.main.id}"
  name = "builders_allow_outbound"
  egress {
    from_port = 0
    to_port   = 0
    protocol  = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}