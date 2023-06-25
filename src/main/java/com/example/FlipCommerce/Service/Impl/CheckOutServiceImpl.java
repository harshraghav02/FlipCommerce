package com.example.FlipCommerce.Service.Impl;

import com.example.FlipCommerce.DTO.RequestDTO.CheckOutCartRequestDTO;
import com.example.FlipCommerce.DTO.RequestDTO.ItemRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.ItemResponseDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.OrderResponseDTO;
import com.example.FlipCommerce.Exception.CardNotValid;
import com.example.FlipCommerce.Exception.CustomerNotFound;
import com.example.FlipCommerce.Exception.EmptyCartException;
import com.example.FlipCommerce.Exception.InsufficientProductQuantity;
import com.example.FlipCommerce.Model.*;
import com.example.FlipCommerce.Repository.CardRepository;
import com.example.FlipCommerce.Repository.CustomerRepository;
import com.example.FlipCommerce.Repository.OrderReposiory;
import com.example.FlipCommerce.Service.CheckOutService;
import com.example.FlipCommerce.Transformer.OrderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;


@Service
public class CheckOutServiceImpl implements CheckOutService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    CardRepository cardRepository;

    @Autowired
    OrderReposiory orderReposiory;


    @Override
    public OrderResponseDTO checkOutCart(CheckOutCartRequestDTO checkOutCartRequestDTO) throws CustomerNotFound, CardNotValid, EmptyCartException, InsufficientProductQuantity {

        Customer customer = customerRepository.findByEmailId(checkOutCartRequestDTO.getCustomerEmailId());
        if(customer == null){
            throw new CustomerNotFound("Customer not Found!!");
        }

        Card card = cardRepository.findByCardNo(checkOutCartRequestDTO.getCardNo());
        Date date = new Date();
        if(card == null || card.getCvv()!=checkOutCartRequestDTO.getCvv() || date.after(card.getValidTill())){
            throw new CardNotValid("Card Details are not matching with database!");
        }

        Cart cart = customer.getCart();

        if(cart.getItemList().size() == 0){
            throw new EmptyCartException("Cart is Empty!!");
        }

        OrderEntity orderEntity = orderService.placeOrder(cart,card);
        resetCart(cart);

        OrderEntity savedOrder = orderReposiory.save(orderEntity);
        customer.getOrderEntityList().add(orderEntity);



        return OrderTransformer.orderToOrderResponseDTO(savedOrder);
    }

    private void resetCart(Cart cart){
        cart.setCartTotal(0);
        for(Item item : cart.getItemList())
            item.setCart(null);
        cart.setItemList(new ArrayList<>());

    }
}

