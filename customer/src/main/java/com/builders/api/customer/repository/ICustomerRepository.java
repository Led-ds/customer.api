package com.builders.api.customer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.builders.api.customer.model.Customer;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findByDocument(String document);
}
