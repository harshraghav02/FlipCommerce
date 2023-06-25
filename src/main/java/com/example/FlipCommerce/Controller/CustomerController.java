package com.example.FlipCommerce.Controller;

import com.example.FlipCommerce.DTO.RequestDTO.CustomerRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.CustomerResponseDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.OrderResponseDTO;
import com.example.FlipCommerce.Exception.CustomerNotFound;
import com.example.FlipCommerce.Exception.NoOrderPlaced;
import com.example.FlipCommerce.Model.Customer;
import com.example.FlipCommerce.Model.OrderEntity;
import com.example.FlipCommerce.Repository.CustomerRepository;
import com.example.FlipCommerce.Service.Impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerServiceImpl customerService;

    @PostMapping("/add")
    public ResponseEntity addCustomer(@RequestBody CustomerRequestDTO customerRequestDTO){
        CustomerResponseDTO customerResponseDTO = customerService.addCustomer(customerRequestDTO);

        return new ResponseEntity(customerResponseDTO, HttpStatus.CREATED);
    }

    // customers whose age is less than 45
    @GetMapping("/get/age/less_than/{age}")
    public ResponseEntity getAgeLessThan(@PathVariable("age") int age){

        List<CustomerResponseDTO> customerResponseDTOList = customerService.getCustomersAgeLessThan(age);

        return new ResponseEntity(customerResponseDTOList,HttpStatus.FOUND);
    }

    // get all female customer between age 20 to 38
    @GetMapping("/get/female/age/between/{from}/{to}")
    public ResponseEntity getFemaleCustomerBetweenAge(@PathVariable("from") int fromAge,@PathVariable("to") int toAge){

        List<CustomerResponseDTO> customerResponseDTOList = customerService.getFemaleCustomerBetweenAge(fromAge,toAge);

        return new ResponseEntity(customerResponseDTOList,HttpStatus.FOUND);
    }


    // get all order of cutomer by its email id
    @GetMapping("/get-order-customer")
    public ResponseEntity getAllOrder(@RequestParam String emailId){
        try{
            List<OrderResponseDTO> orderEntityList = customerService.getAllOrder(emailId);
            return new ResponseEntity(orderEntityList,HttpStatus.FOUND);
        } catch (CustomerNotFound e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        } catch (NoOrderPlaced e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
    }


}
