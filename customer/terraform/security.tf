resource "aws_security_group" "allow_ssh" {
  vpc_id = aws_vpc.main.id
  name = "builders_allow_ssh"

  ingress {
    from_port = 22
    to_port   = 22
    protocol  = "tcp"
    cidr_blocks = ["177.129.228.177/32"]
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