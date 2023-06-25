package com.example.FlipCommerce.Transformer;

import com.example.FlipCommerce.DTO.ResponseDTO.CartResponseDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.ItemResponseDTO;
import com.example.FlipCommerce.Model.Cart;
import com.example.FlipCommerce.Model.Item;

import java.util.ArrayList;
import java.util.List;

public class CartTransformer {
    public static CartResponseDTO cartToCartResponseDTO(Cart cart){

        List<ItemResponseDTO> itemResponseDTOList = new ArrayList<>();
        for(Item item : cart.getItemList()){
            itemResponseDTOList.add(ItemTransformer.itemToItemResponseDTO(item));
        }
        return CartResponseDTO.builder()
                .cartTotal(cart.getCartTotal())
                .customerName(cart.getCustomer().getName())
                .itemResponseDTOList(itemResponseDTOList)
                .build();
    }
}
