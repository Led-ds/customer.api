package com.builders.api.customer.transfer;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO implements Serializable {

	@NotBlank(message = "attribute.name.mandatory")
	private String name;
	
	@NotBlank(message = "attribute.document_type.mandatory")
	private String documentType;
	
	@NotBlank(message = "attribute.document.mandatory")
	private String document;
	
	@NotBlank(message = "attribute.email.mandatory")
	private String email;
	
	@NotBlank(message = "attribute.date_birth.mandatory")
	private String birthday;
	
}
