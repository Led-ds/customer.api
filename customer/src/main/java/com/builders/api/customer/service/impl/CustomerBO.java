package com.builders.api.customer.service.impl;

import static java.util.Objects.isNull;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.builders.api.customer.error.CustomerExceptionHandler;
import com.builders.api.customer.model.Customer;
import com.builders.api.customer.repository.ICustomerRepository;
import com.builders.api.customer.service.ICustomerBO;
import com.builders.api.customer.transfer.CustomerDTO;
import com.builders.api.customer.transfer.CustomerTO;
import com.builders.api.customer.transfer.converte.CustomerParser;

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
		Optional<Customer> customerByDocument = repository.findByDocument(dto.getDocument());
		
		if(customerByDocument.isPresent()) {
			throw new CustomerExceptionHandler("customer.entity.alread.exist.error", dto.getDocument());
		}
		
		Customer newCustomer = repository.save(customerParser.toCustomerEntity(dto));
		
		return customerParser.toCustomerTO(newCustomer); 
	}

	@Override
	public Page<CustomerTO> listAll(Pageable page) {
		Page<Customer> pages = repository.findAll(page);
		
		if (isNull(pages))
			throw new CustomerExceptionHandler("customer.list.empty.error");
		
		return  pages.map(o -> customerParser.toCustomerTO(o));
	}

	@Override
	public CustomerTO edit(Long customerId, CustomerDTO dto) {
		Customer updatingCustomer = customerParser.toUpdateCustomer(repository.getById(customerId), dto);
		
		Customer updatedCustomer = repository.saveAndFlush(updatingCustomer);

        if (isNull(updatedCustomer))
            throw new CustomerExceptionHandler("customer.error.update", dto.getDocument());

        return customerParser.toCustomerTO(updatedCustomer); 
	}

}
