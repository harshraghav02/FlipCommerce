package com.example.FlipCommerce.Transformer;

import com.example.FlipCommerce.DTO.RequestDTO.CardRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.CardResponseDTO;
import com.example.FlipCommerce.Model.Card;

public class CardTransformer {

    public static Card cardToCardRequestDTO(CardRequestDTO cardRequestDTO){
        return Card.builder()
                .cardNo(cardRequestDTO.getCardNo())
                .cardType(cardRequestDTO.getCardType())
                .cvv(cardRequestDTO.getCvv())
                .validTill(cardRequestDTO.getValidTill())
                .build();
    }

    public static CardResponseDTO cardTocardResponseDTO(Card card){
        return CardResponseDTO.builder()
                .cardNo(card.getCardNo())
                .message("Successfully Saved Card")
                .customerName(card.getCustomer().getName())
                .build();
    }
}
