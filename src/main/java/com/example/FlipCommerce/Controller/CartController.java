package com.example.FlipCommerce.Controller;

import com.example.FlipCommerce.DTO.RequestDTO.CheckOutCartRequestDTO;
import com.example.FlipCommerce.DTO.RequestDTO.ItemRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.CartResponseDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.OrderResponseDTO;
import com.example.FlipCommerce.Exception.CardNotValid;
import com.example.FlipCommerce.Exception.CustomerNotFound;
import com.example.FlipCommerce.Exception.EmptyCartException;
import com.example.FlipCommerce.Exception.InsufficientProductQuantity;
import com.example.FlipCommerce.Model.Item;
import com.example.FlipCommerce.Service.Impl.CartServiceImpl;
import com.example.FlipCommerce.Service.Impl.CheckOutServiceImpl;
import com.example.FlipCommerce.Service.Impl.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    ItemServiceImpl itemService;

    @Autowired
    CartServiceImpl cartService;

    @Autowired
    CheckOutServiceImpl checkOutService;
    @PostMapping("/add")
    public ResponseEntity addCart(@RequestBody ItemRequestDTO itemRequestDTO){

        try{
            Item item = itemService.createItem(itemRequestDTO);
            CartResponseDTO cartResponseDTO = cartService.addCart(item,itemRequestDTO);
            return new ResponseEntity(cartResponseDTO,HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity checkOutCart(@RequestBody CheckOutCartRequestDTO checkOutCartRequestDTO) throws EmptyCartException, InsufficientProductQuantity, CustomerNotFound, CardNotValid {
        try{
            OrderResponseDTO orderResponseDTO = checkOutService.checkOutCart(checkOutCartRequestDTO);
            return new ResponseEntity(orderResponseDTO,HttpStatus.FOUND);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
