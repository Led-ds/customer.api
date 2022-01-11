package com.builders.api.customer.transfer.converte;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.builders.api.customer.model.Customer;
import com.builders.api.customer.model.DocumentType;
import com.builders.api.customer.transfer.CustomerDTO;
import com.builders.api.customer.transfer.CustomerTO;

@Component
public class CustomerParser {

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	
	public Customer toCustomerEntity(final CustomerDTO dto) {
		LocalDate localDate =  LocalDate.parse(dto.getBirthday(), formatter);
		
        return Customer.builder()
                .name(dto.getName())
                .documentType(DocumentType.valueOf(dto.getDocumentType()))
                .document(dto.getDocument())
                .email(dto.getEmail())
                .birthday(localDate)
                .build();
    }
	
	public CustomerTO toCustomerTO(final Customer entity) {
        return CustomerTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .documentType(entity.getDocumentType().name())
                .document(entity.getDocument())
                .email(entity.getEmail())
                .dateBith(entity.getBirthday().format(formatter))
                .build();
    }
	
	public Customer toUpdateCustomer(final Customer entity, final CustomerDTO dto) {
		LocalDate localDate =  LocalDate.parse(dto.getBirthday(), formatter);
		
		return Customer.builder()
                .id(entity.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .documentType(DocumentType.valueOf(dto.getDocumentType()))
                .document(dto.getDocument())
                .birthday(localDate)
                .build();
    }
}
