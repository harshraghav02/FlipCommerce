package com.example.FlipCommerce.Service;

import com.example.FlipCommerce.DTO.RequestDTO.ProductRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.ProductResponseDTO;
import com.example.FlipCommerce.Enums.Category;
import com.example.FlipCommerce.Exception.SellerNotFound;

import java.util.List;

public interface ProductService {

    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) throws SellerNotFound;

    public List<ProductResponseDTO> getProductByCategoryAndPrice(Category category,int price);

    public  List<ProductResponseDTO> getTop5CheapestProductByCategory(Category category);
}
