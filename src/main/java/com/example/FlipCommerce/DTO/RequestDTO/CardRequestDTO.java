package com.example.FlipCommerce.DTO.RequestDTO;

import com.example.FlipCommerce.Enums.CardType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CardRequestDTO {

    String customerEmailId;

    String cardNo;

    CardType cardType;

    Date validTill;

    int cvv;

}
