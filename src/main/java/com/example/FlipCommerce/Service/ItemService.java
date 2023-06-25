package com.example.FlipCommerce.Service;

import com.example.FlipCommerce.DTO.RequestDTO.ItemRequestDTO;
import com.example.FlipCommerce.Exception.CustomerNotFound;
import com.example.FlipCommerce.Exception.InsufficientProductQuantity;
import com.example.FlipCommerce.Exception.OutOfStockProduct;
import com.example.FlipCommerce.Exception.ProductNotFound;
import com.example.FlipCommerce.Model.Item;

public interface ItemService {
    Item createItem(ItemRequestDTO itemRequestDTO) throws CustomerNotFound, ProductNotFound, OutOfStockProduct, InsufficientProductQuantity;
}
