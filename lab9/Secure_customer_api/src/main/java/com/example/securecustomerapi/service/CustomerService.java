package com.example.securecustomerapi.service;

import com.example.securecustomerapi.dto.CustomerRequestDTO;
import com.example.securecustomerapi.dto.CustomerResponseDTO;
import com.example.securecustomerapi.dto.CustomerUpdateDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {

    //Pagination + Sorting 
    Page<CustomerResponseDTO> getAllCustomers(int page, int size, String sortBy, String sortDir);

    CustomerResponseDTO getCustomerById(Long id);

    CustomerResponseDTO createCustomer(CustomerRequestDTO requestDTO);

    CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO requestDTO);

    //PATCH
    CustomerResponseDTO partialUpdateCustomer(Long id, CustomerUpdateDTO updateDTO);

    void deleteCustomer(Long id);

    List<CustomerResponseDTO> searchCustomers(String keyword);

    List<CustomerResponseDTO> getCustomersByStatus(String status);

    // advanced search
    List<CustomerResponseDTO> advancedSearch(String name, String email, String status);
}


