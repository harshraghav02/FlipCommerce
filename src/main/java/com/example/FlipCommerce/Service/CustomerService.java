package com.example.FlipCommerce.Service;

import com.example.FlipCommerce.DTO.RequestDTO.CustomerRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.CustomerResponseDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.OrderResponseDTO;
import com.example.FlipCommerce.Exception.CustomerNotFound;
import com.example.FlipCommerce.Exception.NoOrderPlaced;
import com.example.FlipCommerce.Model.Customer;
import com.example.FlipCommerce.Model.OrderEntity;

import java.util.List;

public interface CustomerService {
    public CustomerResponseDTO addCustomer(CustomerRequestDTO customerRequestDTO);
    public List<CustomerResponseDTO> getCustomersAgeLessThan(int age);

    public List<CustomerResponseDTO> getFemaleCustomerBetweenAge(int fromAge,int toAge);

    List<OrderResponseDTO> getAllOrder(String emailId) throws CustomerNotFound, NoOrderPlaced;
}
