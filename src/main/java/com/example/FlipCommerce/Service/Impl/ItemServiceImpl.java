package com.example.FlipCommerce.Service.Impl;

import com.example.FlipCommerce.DTO.RequestDTO.ItemRequestDTO;
import com.example.FlipCommerce.Exception.CustomerNotFound;
import com.example.FlipCommerce.Exception.InsufficientProductQuantity;
import com.example.FlipCommerce.Exception.OutOfStockProduct;
import com.example.FlipCommerce.Exception.ProductNotFound;
import com.example.FlipCommerce.Model.Customer;
import com.example.FlipCommerce.Model.Item;
import com.example.FlipCommerce.Model.Product;
import com.example.FlipCommerce.Repository.CustomerRepository;
import com.example.FlipCommerce.Repository.ProductRepository;
import com.example.FlipCommerce.Service.ItemService;
import com.example.FlipCommerce.Transformer.ItemTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;
    @Override
    public Item createItem(ItemRequestDTO itemRequestDTO) throws CustomerNotFound, ProductNotFound, OutOfStockProduct, InsufficientProductQuantity {
        // check customer

        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.findByEmailId(itemRequestDTO.getCustomerEmailId()));
        if(!optionalCustomer.isPresent()){
            throw new CustomerNotFound("Customer Not Found");
        }

        Customer customer = optionalCustomer.get();
        // check  product

        Optional<Product> optionalProduct = productRepository.findById(itemRequestDTO.getProductId());
        if(!optionalProduct.isPresent()){
            throw new ProductNotFound("Product Not Found");
        }

        Product product = optionalProduct.get();

        if(product.getQuantity() == 0){
            throw new OutOfStockProduct("Product is Out of Stock");
        }
        if(product.getQuantity() < itemRequestDTO.getRequiredQuantity()){
            throw new InsufficientProductQuantity("Insufficient product quantity");
        }

        Item item = ItemTransformer.itemRequestDTOToItem(itemRequestDTO.getRequiredQuantity());

        return item;

    }
}
