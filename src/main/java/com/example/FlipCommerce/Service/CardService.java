package com.example.FlipCommerce.Service;

import com.example.FlipCommerce.DTO.RequestDTO.CardRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.CardResponseDTO;
import com.example.FlipCommerce.Exception.CardNotValid;
import com.example.FlipCommerce.Exception.CustomerNotFound;

public interface CardService {

    public CardResponseDTO addCard(CardRequestDTO cardRequestDTO) throws CustomerNotFound, CardNotValid;
}
