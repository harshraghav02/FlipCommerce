package com.example.FlipCommerce.Service.Impl;

import com.example.FlipCommerce.DTO.RequestDTO.ItemRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.CartResponseDTO;
import com.example.FlipCommerce.Model.Cart;
import com.example.FlipCommerce.Model.Customer;
import com.example.FlipCommerce.Model.Item;
import com.example.FlipCommerce.Model.Product;
import com.example.FlipCommerce.Repository.CartRepository;
import com.example.FlipCommerce.Repository.CustomerRepository;
import com.example.FlipCommerce.Repository.ProductRepository;
import com.example.FlipCommerce.Service.CartService;
import com.example.FlipCommerce.Transformer.CartTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;
    @Override
    public CartResponseDTO addCart(Item item, ItemRequestDTO itemRequestDTO) {

        Customer customer = customerRepository.findByEmailId(itemRequestDTO.getCustomerEmailId());
        Product product = productRepository.findById(itemRequestDTO.getProductId()).get();

        Cart cart = customer.getCart();
        cart.setCartTotal(cart.getCartTotal() + item.getRequiredQuantity()*product.getPrice());
        cart.getItemList().add(item);
        item.setCart(cart);
        item.setProduct(product);

        Cart savedCart = cartRepository.save(cart);
        product.getItemList().add(item);

        return CartTransformer.cartToCartResponseDTO(savedCart);

    }

    @Override
    public Cart addCart(Customer customer) {
        return Cart.builder()
                .customer(customer)
                .cartTotal(0)
                .build();
    }


}
