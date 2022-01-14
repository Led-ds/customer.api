package com.builders.api.customer.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.builders.api.customer.error.CustomerErrorException;
import com.builders.api.customer.model.Customer;
import com.builders.api.customer.model.DocumentType;
import com.builders.api.customer.repository.ICustomerRepository;
import com.builders.api.customer.service.impl.CustomerBO;
import com.builders.api.customer.transfer.CustomerDTO;
import com.builders.api.customer.transfer.CustomerTO;
import com.builders.api.customer.transfer.converte.CustomerParser;

public class CustomerBOTest {

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private CustomerBO customerBO;
	
	private CustomerParser customerParser;
	
	@Mock
	private ICustomerRepository repositoryMocked;
	
	@SuppressWarnings("deprecation")
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		customerBO = new CustomerBO(repositoryMocked);
		customerParser = new CustomerParser();
	}
	
	@Test(expected = CustomerErrorException.class)
	public void should_deny_creation_of_customer_that_exists() {
		Customer customerExists = new Customer();
		customerExists.setId(10L);
		customerExists.setName("Builders");
		customerExists.setDocumentType(DocumentType.CPF);
		customerExists.setDocument("12603521969");
		customerExists.setEmail("builders09@builders.com.br");
		customerExists.setBirthday(LocalDate.parse("09/02/1983", formatter));
		
		when(repositoryMocked.findByDocument("12603521969")).thenReturn(Optional.of(customerExists));
		
		CustomerDTO newCustomer = new CustomerDTO();
		newCustomer.setName("Builders");
		newCustomer.setDocumentType(DocumentType.CPF.name());
		newCustomer.setDocument("12603521969");
		newCustomer.setEmail("builders09@builders.com.br");
		newCustomer.setBirthday("09/02/1983");
		
		customerBO.save(newCustomer);
	}
	
	@Test
	public void should_create_new_customer() {
		Customer newCustomer = new Customer();
		newCustomer.setName("Soares Alex");
		newCustomer.setDocumentType(DocumentType.CPF);
		newCustomer.setDocument("11762661101");
		newCustomer.setEmail("asoares@builders.com.br");
		newCustomer.setBirthday(LocalDate.parse("31/12/2000", formatter));
		
		Customer customerInDataBase = new Customer();
		customerInDataBase.setId(11L);
		customerInDataBase.setName("Soares Alex");
		customerInDataBase.setDocumentType(DocumentType.CPF);
		customerInDataBase.setDocument("11762661101");
		customerInDataBase.setEmail("asoares@builders.com.br");
		customerInDataBase.setBirthday(LocalDate.parse("31/12/2000", formatter));
		
		when(repositoryMocked.save(newCustomer)).thenReturn(customerInDataBase);
		
		Customer customerSaved = repositoryMocked.save(newCustomer);
		
		assertThat(customerSaved.getId(), equalTo(11L));
		assertThat(customerSaved.getName(), equalTo("Soares Alex"));
		assertThat(customerSaved.getDocumentType(), equalTo(DocumentType.CPF));
		assertThat(customerSaved.getDocument(), equalTo("11762661101"));
	}
	
}
