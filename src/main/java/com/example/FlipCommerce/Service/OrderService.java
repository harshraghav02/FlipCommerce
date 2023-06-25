package com.example.FlipCommerce.Service;

import com.example.FlipCommerce.DTO.RequestDTO.OrderRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.OrderResponseDTO;
import com.example.FlipCommerce.Exception.CardNotValid;
import com.example.FlipCommerce.Exception.CustomerNotFound;
import com.example.FlipCommerce.Exception.InsufficientProductQuantity;
import com.example.FlipCommerce.Exception.ProductNotFound;

public interface OrderService {

    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO) throws CustomerNotFound, ProductNotFound, InsufficientProductQuantity, CardNotValid;
}
