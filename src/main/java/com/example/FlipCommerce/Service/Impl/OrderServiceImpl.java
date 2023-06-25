package com.example.FlipCommerce.Service.Impl;

import com.example.FlipCommerce.DTO.RequestDTO.OrderRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.OrderResponseDTO;
import com.example.FlipCommerce.Enums.ProductStatus;
import com.example.FlipCommerce.Exception.CardNotValid;
import com.example.FlipCommerce.Exception.CustomerNotFound;
import com.example.FlipCommerce.Exception.InsufficientProductQuantity;
import com.example.FlipCommerce.Exception.ProductNotFound;
import com.example.FlipCommerce.Model.*;
import com.example.FlipCommerce.Repository.CardRepository;
import com.example.FlipCommerce.Repository.CustomerRepository;
import com.example.FlipCommerce.Repository.OrderReposiory;
import com.example.FlipCommerce.Repository.ProductRepository;
import com.example.FlipCommerce.Service.OrderService;
import com.example.FlipCommerce.Transformer.ItemTransformer;
import com.example.FlipCommerce.Transformer.OrderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    OrderReposiory orderReposiory;
    @Autowired
    JavaMailSender javaMailSender;
    @Override
    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO) throws CustomerNotFound, ProductNotFound, InsufficientProductQuantity, CardNotValid {
        //check customer

        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.findByEmailId(orderRequestDTO.getCustomerEmailId()));

        if(!optionalCustomer.isPresent()){
            throw new CustomerNotFound("Customer Not Exist with given Email ID");
        }
        Customer customer = optionalCustomer.get();

        // check  product

        Optional<Product> optionalProduct = productRepository.findById(orderRequestDTO.getProductId());
        if(!optionalProduct.isPresent()){
            throw new ProductNotFound("Product Not Found");
        }
        Product product = optionalProduct.get();

        // check quantity
        if(product.getQuantity() < orderRequestDTO.getRequiredQuantity()){
            throw new InsufficientProductQuantity("Sorry Product Quantity is less than "+orderRequestDTO.getRequiredQuantity());
        }

        // check card
        Card card = cardRepository.findByCardNo(orderRequestDTO.getCardNo());
        Date date =new Date();
        if(card == null || card.getCvv() != orderRequestDTO.getCvv() || date.after(card.getValidTill())){
            throw new CardNotValid("Card Details are not matching with database" );
        }

        int newQuantity = product.getQuantity() - orderRequestDTO.getRequiredQuantity();
        product.setQuantity(newQuantity);
        if(newQuantity == 0){
            product.setProductStatus(ProductStatus.OUT_OF_STOCK);
        }

        Item item = ItemTransformer.itemRequestDTOToItem(orderRequestDTO.getRequiredQuantity());
        item.setProduct(product);
        OrderEntity orderEntity = OrderTransformer.orderRequestDTOtoOrder(customer,item);
        String maskedCard = "XXXX-XXXX-XXXX-" + card.getCardNo().substring(12);

        orderEntity.setCardUsed(maskedCard);
        orderEntity.getItemList().add(item);

        item.setOrderEntity(orderEntity);

        OrderEntity savedOrder = orderReposiory.save(orderEntity); // save both order and item
        customer.getOrderEntityList().add(savedOrder);
        product.getItemList().add(product.getItemList().get(0));

        // send mail to customer
        String text = "You have successfully booked your order. Below are order details:"+"\n"+"\n"
                +"Customer Name : " + customer.getName() + "\n" + "\n"
                +"Order Number : " + savedOrder.getOrderNo() + "\n" + "\n"
                +"Total Order Value : " + savedOrder.getTotalValue() + "\n" + "\n"
                +"Card Used : " + savedOrder.getCardUsed() + "\n" + "\n"
                +"Order Date : " + savedOrder.getOrderDate().toString() + "\n" + "\n"
                +"Product Details : " +"\n"+"\n" + "      ";
        for(Item item1 : savedOrder.getItemList()){
            text += "Product Name : " + item1.getProduct().getName() +"\n" + "      ";
            text += "Product price : " + item1.getProduct().getPrice() + "\n" +"\n" +"      ";
        }

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setText(text);
        simpleMailMessage.setSubject("Order Booked Successfully");
        simpleMailMessage.setFrom("puublicstaticvoidmain@gmail.com");
        simpleMailMessage.setTo(customer.getEmailId());
        javaMailSender.send(simpleMailMessage);
        // prepare response DTO

        return OrderTransformer.orderToOrderResponseDTO(orderEntity);


    }

    public OrderEntity placeOrder(Cart cart,Card card) throws InsufficientProductQuantity {
        OrderEntity orderEntity= new OrderEntity();
        orderEntity.setOrderNo(String.valueOf(UUID.randomUUID()));
        orderEntity.setCardUsed("XXXX-XXXX-XXXX-"+card.getCardNo().substring(12));

        int totalValue= 0;

        for(Item item : cart.getItemList()){
            Product product = item.getProduct();
            if(product.getQuantity() < item.getRequiredQuantity()){
                throw new InsufficientProductQuantity("Insufficient Quantity of Product :"+ product.getName());
            }
            totalValue +=item.getRequiredQuantity()*product.getPrice();
            int newQuantity = product.getQuantity() - item.getRequiredQuantity();
            product.setQuantity(newQuantity);
            if(newQuantity == 0){
                product.setProductStatus(ProductStatus.OUT_OF_STOCK);
            }

            item.setOrderEntity(orderEntity);

        }

        orderEntity.setTotalValue(totalValue);
        orderEntity.setItemList(cart.getItemList());
        orderEntity.setCustomer(cart.getCustomer());

        // send mail to customer
        String text = "You have successfully booked your order. Below are order details:"+"\n"+"\n"
                +"Customer Name : " + card.getCustomer().getName() + "\n" + "\n"
                +"Order Number : " + orderEntity.getOrderNo() + "\n" + "\n"
                +"Total Order Value : " + orderEntity.getTotalValue() + "\n" + "\n"
                +"Card Used : " + orderEntity.getCardUsed() + "\n" + "\n"
                +"Order Date : " + orderEntity.getOrderDate() + "\n" + "\n"
                +"Product Details : " +"\n"+"\n" + "      ";
        for(Item item1 : orderEntity.getItemList()){
            text += "Product Name : " + item1.getProduct().getName() +"\n" + "      ";
            text += "Product price : " + item1.getProduct().getPrice() + "\n" +"\n" +"      ";
        }

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setText(text);
        simpleMailMessage.setSubject("Order Booked Successfully");
        simpleMailMessage.setFrom("puublicstaticvoidmain@gmail.com");
        simpleMailMessage.setTo(card.getCustomer().getEmailId());
        javaMailSender.send(simpleMailMessage);
        return orderEntity;
    }
}
