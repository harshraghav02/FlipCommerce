package com.example.FlipCommerce.Transformer;

import com.example.FlipCommerce.DTO.RequestDTO.ProductRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.ProductResponseDTO;
import com.example.FlipCommerce.Enums.ProductStatus;
import com.example.FlipCommerce.Model.Product;
import com.example.FlipCommerce.Model.Seller;
import com.example.FlipCommerce.Repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductTransformer {


    public static Product productRequestDTOToProduct(ProductRequestDTO productRequestDTO,Seller seller){

        return Product.builder()
                .name(productRequestDTO.getName())
                .price(productRequestDTO.getPrice())
                .quantity(productRequestDTO.getQuantity())
                .productStatus(ProductStatus.AVAILABLE)
                .category(productRequestDTO.getCategory())
                .seller(seller)
                .build();
    }

    public  static ProductResponseDTO productToProductResponseDTO(Product product){
        return ProductResponseDTO.builder()
                .productName(product.getName())
                .price(product.getPrice())
                .productStatus(product.getProductStatus())
                .sellerName(product.getSeller().getName())
                .category(product.getCategory())
                .build();
    }
}
