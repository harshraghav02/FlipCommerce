package com.example.FlipCommerce.DTO.RequestDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CheckOutCartRequestDTO {

    String customerEmailId;

    String cardNo;

    int cvv;

}
