resource "aws_key_pair" "keypair"{
  public_key = "${file("key/builders_key.pub")}"
}

resource "aws_instance" "instances" {
  count = 3

  ami = "ami-0b0af3577fe5e3532"

  instance_type = "t1.micro"

  subnet_id = "${element(aws_subnet.public_subnet.*.id, count.index)}"

  key_name = "${aws_key_pair.keypair.key_name}"

  vpc_security_group_ids = ["${aws_security_group.allow_ssh.id}"]

  tags = {Name = "builders_instances"}
}

output "public_ips" {
  value = "${join(", ", aws_instance.instances.*.public_ip)}"
}