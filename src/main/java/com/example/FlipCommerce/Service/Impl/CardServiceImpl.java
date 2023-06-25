package com.example.FlipCommerce.Service.Impl;

import com.example.FlipCommerce.DTO.RequestDTO.CardRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.CardResponseDTO;
import com.example.FlipCommerce.Exception.CardNotValid;
import com.example.FlipCommerce.Exception.CustomerNotFound;
import com.example.FlipCommerce.Model.Card;
import com.example.FlipCommerce.Model.Customer;
import com.example.FlipCommerce.Repository.CustomerRepository;
import com.example.FlipCommerce.Service.CardService;
import com.example.FlipCommerce.Transformer.CardTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    CustomerRepository customerRepository;
    @Override
    public CardResponseDTO addCard(CardRequestDTO cardRequestDTO) throws CustomerNotFound, CardNotValid {

        // check customer
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.findByEmailId(cardRequestDTO.getCustomerEmailId()));

        if(!optionalCustomer.isPresent()){
            throw new CustomerNotFound("Customer Not Exist with given Email ID");
        }
        Customer customer = optionalCustomer.get();
        // check cardNo -> card no should be of 16 digits with only digit

        String cardNo = cardRequestDTO.getCardNo();
        if(cardNo.length() != 16)
            throw new CardNotValid("Card Number is Not Valid");

        for(char c : cardNo.toCharArray()){
            if(c<47 || c>57) {
                throw new CardNotValid("Card Number is Not Valid");

            }
        }
        // dto -> entity
        Card card = CardTransformer.cardToCardRequestDTO(cardRequestDTO);
        card.setCustomer(customer);

        customer.getCardList().add(card);

        customerRepository.save(customer); // save customer and card

        Card savedCard = customer.getCardList().get(customer.getCardList().size()-1);

        // entity -> dto

        CardResponseDTO cardResponseDTO = CardTransformer.cardTocardResponseDTO(savedCard);
        return cardResponseDTO;
    }
}
