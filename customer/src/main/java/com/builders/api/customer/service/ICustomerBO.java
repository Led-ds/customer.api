package com.builders.api.customer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.builders.api.customer.transfer.CustomerDTO;
import com.builders.api.customer.transfer.CustomerTO;

public interface ICustomerBO {

	CustomerTO save(final CustomerDTO dto);

    Page<CustomerTO> listAll(Pageable page);

    CustomerTO edit(Long customerId, CustomerDTO dto);
	
}
