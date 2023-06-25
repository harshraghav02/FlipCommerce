package com.example.FlipCommerce.Service;

import com.example.FlipCommerce.DTO.RequestDTO.SellerRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.ProductResponseDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.SellerResposeDTO;
import com.example.FlipCommerce.Enums.Category;
import com.example.FlipCommerce.Exception.SellerNotFound;

import java.util.List;

public interface SellerService {
    public SellerResposeDTO addSeller(SellerRequestDTO sellerRequestDTO);

    public SellerResposeDTO updateSeller(String emailId,String name,String mobNo) throws SellerNotFound;

    public List<SellerResposeDTO> getAllSellerByCategory(Category category);

    List<ProductResponseDTO> getALlProductSoldBySellerInCategory(String sellerEmailId, Category category) throws SellerNotFound;
}
