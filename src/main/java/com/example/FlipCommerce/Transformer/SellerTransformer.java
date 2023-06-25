package com.example.FlipCommerce.Transformer;

import com.example.FlipCommerce.DTO.RequestDTO.SellerRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.SellerResposeDTO;
import com.example.FlipCommerce.Model.Seller;

public class SellerTransformer {
    public static Seller sellerRequestDTOtoSeller(SellerRequestDTO sellerRequestDTO){
        return Seller.builder()
                .name(sellerRequestDTO.getName())
                .mobNo(sellerRequestDTO.getMobNo())
                .emailId(sellerRequestDTO.getEmailId())
                .build();
    }

    public static SellerResposeDTO sellerToSellerResposeDTO(Seller seller){
        return SellerResposeDTO.builder()
                .name(seller.getName())
                .mobNo(seller.getMobNo())
                .build();
    }
}
