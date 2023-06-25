package com.example.FlipCommerce.DTO.ResponseDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CartResponseDTO {

    int cartTotal;

    String customerName;

    List<ItemResponseDTO> itemResponseDTOList;
}
