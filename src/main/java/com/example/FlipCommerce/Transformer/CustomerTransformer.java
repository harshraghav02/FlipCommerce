package com.example.FlipCommerce.Transformer;

import com.example.FlipCommerce.DTO.RequestDTO.CustomerRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.CustomerResponseDTO;
import com.example.FlipCommerce.Model.Customer;

public class CustomerTransformer {
    public static Customer cutomerRequestDTOTOCustomer(CustomerRequestDTO customerRequestDTO){
        return Customer.builder()
                .mobNo(customerRequestDTO.getMobNo())
                .emailId(customerRequestDTO.getEmailId())
                .name(customerRequestDTO.getName())
                .gender(customerRequestDTO.getGender())
                .age(customerRequestDTO.getAge())
                .build();
    }

    public static CustomerResponseDTO customerToCustomerResponseDTO(Customer customer){
        return CustomerResponseDTO.builder()
                .emailId(customer.getEmailId())
                .mobNo(customer.getMobNo())
                .name(customer.getName())
                .build();
    }
}
