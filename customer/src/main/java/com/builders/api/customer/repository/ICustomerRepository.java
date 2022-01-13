package com.builders.api.customer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.builders.api.customer.model.Customer;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {

	@Query("SELECT c FROM Customer c WHERE c.name LIKE %?1% "
									+ " OR c.documentType LIKE %?1%  "
									+ " OR c.document LIKE %?1% "
									+ " OR c.email LIKE %?1% "
									+ " ORDER BY c.id DESC")
	List<Customer> findByFilter(String key);
	
	Optional<Customer> findByDocument(String document);
}
