resource "aws_key_pair" "keypair"{
  public_key = "${file("key/builders_key.pub")}"
}

resource "aws_instance" "instances" {
  count = 3

  ami = "ami-0b0af3577fe5e3532"

  instance_type = "t1.micro"

  subnet_id = "${element(aws_subnet.public_subnet.*.id, count.index)}"

  key_name = "${aws_key_pair.keypair.key_name}"

  vpc_security_group_ids = ["${aws_security_group.allow_ssh.id}",
                            "${aws_security_group.allow_outbound.id}",
                            "${aws_security_group.cluster_communication.id}",
                            "${aws_security_group.allow_portainer.id}" ]

  tags = {Name = "builders_instances"}
}

data "template_file" "hosts" {
  template = "${file("./template/hosts.tpl")}"

  vars = {
    PUBLIC_IP_0 = "${aws_instance.instances.*.public_ip[0]}"
    PUBLIC_IP_1 = "${aws_instance.instances.*.public_ip[1]}"
    PUBLIC_IP_2 = "${aws_instance.instances.*.public_ip[2]}"

    PRIVATE_IP_0 = "${aws_instance.instances.*.private_ip[0]}"
  }
}

resource "local_file" "hosts" {
  content = "${data.template_file.hosts.rendered}" #força aguardar o processo no data template encerrar
  filename = "./hosts"
}

output "public_ips" {
  value = "${join(", ", aws_instance.instances.*.public_ip)}"
}