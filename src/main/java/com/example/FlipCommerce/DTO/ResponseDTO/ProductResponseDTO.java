package com.example.FlipCommerce.DTO.ResponseDTO;

import com.example.FlipCommerce.Enums.Category;
import com.example.FlipCommerce.Enums.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProductResponseDTO {

    String productName;

    String sellerName;

    Category category;

    int price;

    ProductStatus productStatus;
}
