package com.example.FlipCommerce.DTO.RequestDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class OrderRequestDTO {

    String customerEmailId;

    int productId;

    String cardNo;

    int cvv;

    int requiredQuantity;
}
