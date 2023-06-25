package com.example.FlipCommerce.Transformer;

import com.example.FlipCommerce.DTO.ResponseDTO.ItemResponseDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.OrderResponseDTO;
import com.example.FlipCommerce.Model.Customer;
import com.example.FlipCommerce.Model.Item;
import com.example.FlipCommerce.Model.OrderEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderTransformer {
    public static OrderEntity orderRequestDTOtoOrder(Customer customer, Item item){
        return OrderEntity.builder()
                .orderNo(String.valueOf(UUID.randomUUID()))
                .customer(customer)
                .itemList(new ArrayList<>())
                .totalValue(item.getRequiredQuantity()*item.getProduct().getPrice())
                .build();
    }

    public static OrderResponseDTO orderToOrderResponseDTO(OrderEntity orderEntity){

        List<ItemResponseDTO> itemResponseDTOList = new ArrayList<>();

        for(Item item : orderEntity.getItemList()){
            itemResponseDTOList.add(ItemTransformer.itemToItemResponseDTO(item));
        }
        return OrderResponseDTO.builder()
                .orderNo(orderEntity.getOrderNo())
                .totalValue(orderEntity.getTotalValue())
                .cardUsed(orderEntity.getCardUsed())
                .customerName(orderEntity.getCustomer().getName())
                .orderDate(orderEntity.getOrderDate())
                .itemResponseDTOList(itemResponseDTOList)
                .build();
    }
}
