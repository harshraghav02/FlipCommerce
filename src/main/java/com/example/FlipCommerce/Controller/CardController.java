package com.example.FlipCommerce.Controller;

import com.example.FlipCommerce.DTO.RequestDTO.CardRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.CardResponseDTO;
import com.example.FlipCommerce.Exception.CardNotValid;
import com.example.FlipCommerce.Exception.CustomerNotFound;
import com.example.FlipCommerce.Service.Impl.CardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    CardServiceImpl cardService;
    @PostMapping("add")
    public ResponseEntity addCard(@RequestBody CardRequestDTO cardRequestDTO){

        try{
            CardResponseDTO cardResponseDTO = cardService.addCard(cardRequestDTO);
            return new ResponseEntity(cardResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.CREATED);
        }
    }

}
