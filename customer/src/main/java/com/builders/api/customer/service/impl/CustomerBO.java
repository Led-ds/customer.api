package com.builders.api.customer.service.impl;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.builders.api.customer.error.CustomerErrorException;
import com.builders.api.customer.model.Customer;
import com.builders.api.customer.model.DocumentType;
import com.builders.api.customer.repository.ICustomerRepository;
import com.builders.api.customer.service.ICustomerBO;
import com.builders.api.customer.transfer.CustomerDTO;
import com.builders.api.customer.transfer.CustomerTO;
import com.builders.api.customer.transfer.converte.CustomerParser;
import com.builders.api.customer.util.ValidateDocument;

@Service
public class CustomerBO implements ICustomerBO {

	private ICustomerRepository repository;
	
	@Autowired
	private CustomerParser customerParser;
	
	public CustomerBO(@Autowired ICustomerRepository iCustomerRepository) {
		this.repository = iCustomerRepository;
	}
	
	@Override
	public CustomerTO save(CustomerDTO dto) {
		validadeCustomer(dto);
		
		Optional<Customer> customerByDocument = repository.findByDocument(dto.getDocument());
		
		if(customerByDocument.isPresent()) {
			throw new CustomerErrorException("customer.entity.alread.exist.error", HttpStatus.BAD_REQUEST);
		}
		
		Customer newCustomer = repository.save(customerParser.toCustomerEntity(dto));
		
		return customerParser.toCustomerTO(newCustomer); 
	}

	@Override
	public Page<CustomerTO> listAll(Pageable page) {
		Page<Customer> pages = repository.findAll(page);
		
		if (isNull(pages))
			throw new CustomerErrorException("customer.list.empty.error", HttpStatus.BAD_REQUEST);
		
		return  pages.map(o -> customerParser.toCustomerTO(o));
	}

	@Override
	public CustomerTO edit(Long customerId, CustomerDTO dto) {
		validadeCustomer(dto);
		
		Customer updatingCustomer = customerParser.toUpdateCustomer(repository.getById(customerId), dto);
		
		Customer updatedCustomer = repository.saveAndFlush(updatingCustomer);

        if (isNull(updatedCustomer))
            throw new CustomerErrorException("customer.error.update", HttpStatus.BAD_REQUEST);

        return customerParser.toCustomerTO(updatedCustomer); 
	}

	public void validadeCustomer(CustomerDTO dto) {
		
		if(DocumentType.CPF.name().equals(dto.getDocumentType()) && !ValidateDocument.isCPFValid(dto.getDocument())) {
			throw new CustomerErrorException("customer.document.cpf.not.valid", HttpStatus.BAD_REQUEST);
		}else
			if(DocumentType.CNPJ.name().equals(dto.getDocumentType()) && !ValidateDocument.isCNPJValid(dto.getDocument())) {
				throw new CustomerErrorException("customer.document.cnpj.not.valid", HttpStatus.BAD_REQUEST);
		}
		
		if(!ValidateDocument.isEmailValid(dto.getEmail())) {
			throw new CustomerErrorException("customer.document.email.not.valid", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public List<CustomerTO> findByFilter(String key) {
		
		if(isNull(key) || key.isEmpty())
			throw new CustomerErrorException("customer.filter.empty.error", HttpStatus.BAD_REQUEST);
		
		List<Customer> pages = repository.findByFilter(key);
		
		if (isNull(pages))
			throw new CustomerErrorException("customer.list.empty.error", HttpStatus.BAD_REQUEST);
		
		List<CustomerTO> listTO = new ArrayList<CustomerTO>();
		
		pages.forEach(o -> {
			CustomerTO to = customerParser.toCustomerTO(o);
			listTO.add(to);
		});
		
		return listTO;
	}

}
