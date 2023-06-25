package com.example.FlipCommerce.Transformer;

import com.example.FlipCommerce.DTO.RequestDTO.ItemRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.ItemResponseDTO;
import com.example.FlipCommerce.Model.Customer;
import com.example.FlipCommerce.Model.Item;
import com.example.FlipCommerce.Model.Product;

public class ItemTransformer  {
    public static Item itemRequestDTOToItem( int requiredQuantity){
        return Item.builder()
                .requiredQuantity(requiredQuantity)
                .build();
    }

    public static ItemResponseDTO itemToItemResponseDTO(Item item){
        return ItemResponseDTO.builder()
                .price(item.getProduct().getPrice())
                .productName(item.getProduct().getName())
                .quantityAdded(item.getRequiredQuantity())
                .build();
    }
}
