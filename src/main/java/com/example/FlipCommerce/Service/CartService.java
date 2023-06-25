package com.example.FlipCommerce.Service;

import com.example.FlipCommerce.DTO.RequestDTO.ItemRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.CartResponseDTO;
import com.example.FlipCommerce.Model.Cart;
import com.example.FlipCommerce.Model.Customer;
import com.example.FlipCommerce.Model.Item;

public interface CartService {
    public CartResponseDTO addCart(Item item, ItemRequestDTO itemRequestDTO);
    public Cart addCart(Customer customer);
}
