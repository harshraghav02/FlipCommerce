package com.example.FlipCommerce.Service.Impl;

import com.example.FlipCommerce.DTO.RequestDTO.CustomerRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.CustomerResponseDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.OrderResponseDTO;
import com.example.FlipCommerce.Exception.CustomerNotFound;
import com.example.FlipCommerce.Exception.NoOrderPlaced;
import com.example.FlipCommerce.Model.Cart;
import com.example.FlipCommerce.Model.Customer;
import com.example.FlipCommerce.Model.OrderEntity;
import com.example.FlipCommerce.Repository.CustomerRepository;
import com.example.FlipCommerce.Repository.OrderReposiory;
import com.example.FlipCommerce.Service.CustomerService;
import com.example.FlipCommerce.Transformer.CustomerTransformer;
import com.example.FlipCommerce.Transformer.OrderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired CartServiceImpl cartService;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    JavaMailSender javaMailSender;
    @Override
    public CustomerResponseDTO addCustomer(CustomerRequestDTO customerRequestDTO) {
        // dto -> entity
        Customer customer = CustomerTransformer.cutomerRequestDTOTOCustomer(customerRequestDTO);
        Cart cart = cartService.addCart(customer);
        customer.setCart(cart);

        // save
        Customer savedCustomer = customerRepository.save(customer); // save cart and customer

        // send mail of to customer of successfully registration
        String text = "Congratulations! You SuccessFully Registered on FlipCommerce" + "\n" + "\n"
                + "Now you can add your card details and place your orders" +"\n" + "\n"
                + "Welcome to flipCommerce.";
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setText(text);
        simpleMailMessage.setFrom("puublicstaticvoidmain@gmail.com");
        simpleMailMessage.setTo(savedCustomer.getEmailId());
        simpleMailMessage.setSubject("Registration Successfull");
        javaMailSender.send(simpleMailMessage);

        // entity -> dto

        return CustomerTransformer.customerToCustomerResponseDTO(savedCustomer);
    }

    @Override
    public List<CustomerResponseDTO> getCustomersAgeLessThan(int age) {
        List<Customer> customerList = customerRepository.findByAgeLessThan(age);

        List<CustomerResponseDTO> customerResponseDTOList = new ArrayList<>();

        for(Customer customer : customerList){
            customerResponseDTOList.add(CustomerTransformer.customerToCustomerResponseDTO(customer));
        }

        return customerResponseDTOList;
    }

    @Override
    public List<CustomerResponseDTO> getFemaleCustomerBetweenAge(int fromAge, int toAge) {

        List<Customer> customerList = customerRepository.getFemaleCustomerAgeBetween(fromAge,toAge);

        List<CustomerResponseDTO> customerResponseDTOList = new ArrayList<>();

        for(Customer customer : customerList){
            customerResponseDTOList.add(CustomerTransformer.customerToCustomerResponseDTO(customer));
        }

        return customerResponseDTOList;
    }

    @Override
    public List<OrderResponseDTO> getAllOrder(String emailId) throws CustomerNotFound, NoOrderPlaced {

        // check customer
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.findByEmailId(emailId));
        if(!optionalCustomer.isPresent()){
            throw new CustomerNotFound("Incorrect Customer Mail Id!");
        }

        Customer customer = optionalCustomer.get();
        if(customer.getOrderEntityList().size() == 0){
            throw new NoOrderPlaced("No Order Placed by Customer");
        }
        List<OrderEntity> orderEntityList = customer.getOrderEntityList();
        List<OrderResponseDTO> orderResponseDTOList = new ArrayList<>();

        for(OrderEntity order : orderEntityList){
            orderResponseDTOList.add(OrderTransformer.orderToOrderResponseDTO(order));
        }

        return orderResponseDTOList;
    }


}
