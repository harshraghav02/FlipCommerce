package com.example.FlipCommerce.Service.Impl;

import com.example.FlipCommerce.DTO.RequestDTO.SellerRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.ProductResponseDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.SellerResposeDTO;
import com.example.FlipCommerce.Enums.Category;
import com.example.FlipCommerce.Exception.SellerNotFound;
import com.example.FlipCommerce.Model.Product;
import com.example.FlipCommerce.Model.Seller;
import com.example.FlipCommerce.Repository.SellerRepository;
import com.example.FlipCommerce.Service.SellerService;
import com.example.FlipCommerce.Transformer.ProductTransformer;
import com.example.FlipCommerce.Transformer.SellerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    SellerRepository sellerRepository;
    @Override
    public SellerResposeDTO addSeller(SellerRequestDTO sellerRequestDTO) {

        // dto -> entity
        Seller seller = SellerTransformer.sellerRequestDTOtoSeller(sellerRequestDTO);

        // save in db
        Seller savedSeller = sellerRepository.save(seller);

        // entity -> dto
        return SellerTransformer.sellerToSellerResposeDTO(savedSeller);
    }

    @Override
    public SellerResposeDTO updateSeller(String emailId,String name,String mobNo) throws SellerNotFound {
        // validate seller
        Optional<Seller> optionalSeller = Optional.ofNullable(sellerRepository.findByEmailId(emailId));
        if(!optionalSeller.isPresent()){
            throw new SellerNotFound("Sorry seller not found");
        }

        Seller seller = optionalSeller.get();

        seller.setName(name);
        seller.setMobNo(mobNo);

        return SellerTransformer.sellerToSellerResposeDTO(seller);

    }

    @Override
    public List<SellerResposeDTO> getAllSellerByCategory(Category category) {
        List<Seller> sellerList = sellerRepository.findAllByCategory(category);

        ArrayList<SellerResposeDTO> sellerResposeDTOArrayList = new ArrayList<>();

        for(Seller seller : sellerList){
            sellerResposeDTOArrayList.add(SellerTransformer.sellerToSellerResposeDTO(seller));
        }
        return sellerResposeDTOArrayList;
    }

    @Override
    public List<ProductResponseDTO> getALlProductSoldBySellerInCategory(String sellerEmailId, Category category) throws SellerNotFound {

        // check seller
        Optional<Seller> optionalSeller = Optional.ofNullable(sellerRepository.findByEmailId(sellerEmailId));
        if(!optionalSeller.isPresent()){
            throw new SellerNotFound("Seller Email id not found");
        }

        Seller seller = optionalSeller.get();
        List<Product> productList = seller.getProductList();

        List<ProductResponseDTO> productResponseDTOList = new ArrayList<>();

        for(Product product : productList){
            if(product.getCategory() == category){
                productResponseDTOList.add(ProductTransformer.productToProductResponseDTO(product));
            }
        }

        return productResponseDTOList;
    }
}
