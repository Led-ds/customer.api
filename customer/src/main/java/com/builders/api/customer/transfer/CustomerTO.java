package com.builders.api.customer.transfer;

import java.io.Serializable;

import com.builders.api.customer.model.DocumentType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name", "documentType", "document", "email", "dateBith"})
public class CustomerTO implements Serializable {

	private Long id;
	
	private String name;
	
	private String documentType;
	
	private String document;
	
	private String email;
	
	private String dateBith;
	
}
