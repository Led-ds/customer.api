package com.builders.api.customer.adapter;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.builders.api.customer.service.ICustomerBO;
import com.builders.api.customer.transfer.CustomerDTO;
import com.builders.api.customer.transfer.CustomerTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerRest {

	@Autowired
	private ICustomerBO iCustomerBO;
	
	private final PagedResourcesAssembler<CustomerTO> pagedResourcesAssembler;
	

	@ApiOperation(value = "Create Customer", response = CustomerTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Create Success Customer"),
			@ApiResponse(code = 400, message = "Invalid data Customer"),
			@ApiResponse(code = 500, message = "Error internal server")
	})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerTO create(@ApiParam(value = "Customer apply", required = true)
	@Valid @RequestBody CustomerDTO request) {

		return iCustomerBO.save(request);
	}


	@ApiOperation(value = "Update Customer",  response = CustomerTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Customer Updated"),
			@ApiResponse(code = 400, message = "Invalid data Customer"),
			@ApiResponse(code = 500, message = "Error internal server")
	})
	@PutMapping("/{id}")
	public CustomerTO update(@ApiParam(value = "Customer Update", required = true)
	@PathVariable("id") @NotNull(message = "{attribute.id.mandatory}") final Long id, 
	@Valid @RequestBody final CustomerDTO request) {

		return iCustomerBO.edit(id, request);
	}

	
	@ApiOperation(value = "Search Customer", response = Page.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Customers ListAll"),
			@ApiResponse(code = 400, message = "Invalid data Customer"),
			@ApiResponse(code = 500, message = "Error internal server")
	})
	@GetMapping
	public ResponseEntity<?> list(@ApiParam("Customer Pageable")
						   @PageableDefault( sort = "id", 
						   					 direction = Sort.Direction.DESC, 
						   					 page = 0, 
						   					 size = 10) final Pageable page){

		Page<CustomerTO> listCustomer = iCustomerBO.listAll(page);

		return new ResponseEntity<>(pagedResourcesAssembler.toModel(listCustomer), HttpStatus.OK);
	}
	
	@GetMapping("/filter")
	public List<CustomerTO> findByFilter(@RequestParam(required = false) String key){
		
		List<CustomerTO> listCustomerFilter = iCustomerBO.findByFilter(key);
		
		return listCustomerFilter;
	}
	

}
