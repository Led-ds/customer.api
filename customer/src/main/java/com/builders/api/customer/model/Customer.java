package com.builders.api.customer.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Customer {

	@Id
	@SequenceGenerator(name = "customer_seq", sequenceName = "customer_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "DOCUMENT_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private DocumentType documentType;
	
	@Column(name = "DOCUMENT", nullable = false)
	private String document;
	
	@Column(name = "EMAIL", nullable = false)
	private String email;
	
	@Column(name = "DATE_BIRTH", nullable = false)
	private LocalDate birthday;

}
