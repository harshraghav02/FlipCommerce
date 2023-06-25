package com.example.FlipCommerce.Service;

import com.example.FlipCommerce.DTO.RequestDTO.CheckOutCartRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.OrderResponseDTO;
import com.example.FlipCommerce.Exception.CardNotValid;
import com.example.FlipCommerce.Exception.CustomerNotFound;
import com.example.FlipCommerce.Exception.EmptyCartException;
import com.example.FlipCommerce.Exception.InsufficientProductQuantity;

public interface CheckOutService {
    public OrderResponseDTO checkOutCart(CheckOutCartRequestDTO checkOutCartRequestDTO) throws CustomerNotFound, CardNotValid, EmptyCartException, InsufficientProductQuantity;
}
