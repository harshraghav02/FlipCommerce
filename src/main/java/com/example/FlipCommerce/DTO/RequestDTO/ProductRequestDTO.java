package com.example.FlipCommerce.DTO.RequestDTO;

import com.example.FlipCommerce.Enums.Category;
import com.example.FlipCommerce.Enums.ProductStatus;
import com.example.FlipCommerce.Model.Seller;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProductRequestDTO {

    String sellerEmailId;
    String name;

    Integer price;

    Integer quantity;

    Category category;
}
