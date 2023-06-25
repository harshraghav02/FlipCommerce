package com.example.FlipCommerce.DTO.RequestDTO;

import com.example.FlipCommerce.Enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CustomerRequestDTO {

    String name;

    String mobNo;

    String emailId;

    int age;

    Gender gender;
}
