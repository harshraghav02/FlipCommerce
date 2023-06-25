package com.example.FlipCommerce.Controller;

import com.example.FlipCommerce.DTO.RequestDTO.OrderRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.OrderResponseDTO;
import com.example.FlipCommerce.Service.Impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;
    @PostMapping("/place")
    public ResponseEntity placeOrder(@RequestBody OrderRequestDTO orderRequestDTO){

        try{
            OrderResponseDTO orderResponseDTO = orderService.placeOrder(orderRequestDTO);
            return new ResponseEntity(orderResponseDTO, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
