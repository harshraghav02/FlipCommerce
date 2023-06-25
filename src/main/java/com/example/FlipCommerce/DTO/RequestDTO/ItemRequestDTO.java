package com.example.FlipCommerce.DTO.RequestDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ItemRequestDTO {

    String customerEmailId;

    int productId;

    int requiredQuantity;
}
