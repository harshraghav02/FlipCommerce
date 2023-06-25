package com.example.FlipCommerce.Service.Impl;

import com.example.FlipCommerce.DTO.RequestDTO.ProductRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.ProductResponseDTO;
import com.example.FlipCommerce.Enums.Category;
import com.example.FlipCommerce.Exception.SellerNotFound;
import com.example.FlipCommerce.Model.Product;
import com.example.FlipCommerce.Model.Seller;
import com.example.FlipCommerce.Repository.ProductRepository;
import com.example.FlipCommerce.Repository.SellerRepository;
import com.example.FlipCommerce.Service.ProductService;
import com.example.FlipCommerce.Transformer.ProductTransformer;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    ProductRepository productRepository;
    @Override
    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) throws SellerNotFound {

        // check seller
        Seller seller = sellerRepository.findByEmailId(productRequestDTO.getSellerEmailId());
        if(seller == null){
            throw new SellerNotFound("Seller Not Found");
        }
        // dto -> entity
        Product product = ProductTransformer.productRequestDTOToProduct(productRequestDTO,seller);

        seller.getProductList().add(product);
        // save
        //Product savedProduct = productRepository.save(product);

        Seller savedSeller = sellerRepository.save(seller); // save both seller and product as seller is parent

        Product savedProduct = savedSeller.getProductList().get(savedSeller.getProductList().size()-1);

        // dto -> entity
        return ProductTransformer.productToProductResponseDTO(savedProduct);

    }

    @Override
    public List<ProductResponseDTO> getProductByCategoryAndPrice(Category category, int price) {

        List<Product> productList = productRepository.findByCategoryAndPrice(category,price);

        List<ProductResponseDTO> productResponseDTOList = new ArrayList<>();

        for(Product product : productList){
            productResponseDTOList.add(ProductTransformer.productToProductResponseDTO(product));
        }

        return productResponseDTOList;
    }

    @Override
    public List<ProductResponseDTO> getTop5CheapestProductByCategory(Category category) {

        String categoryInString = category.toString();
        List<Product> productList = productRepository.getTop5ProductCheapestByCategory(categoryInString);

        List<ProductResponseDTO> productResponseDTOList = new ArrayList<>();

        for(Product product : productList){
            productResponseDTOList.add(ProductTransformer.productToProductResponseDTO(product));
        }

        return productResponseDTOList;

    }


}
