package com.example.FlipCommerce.Controller;

import com.example.FlipCommerce.DTO.RequestDTO.ProductRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.ProductResponseDTO;
import com.example.FlipCommerce.Enums.Category;
import com.example.FlipCommerce.Exception.SellerNotFound;
import com.example.FlipCommerce.Service.Impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class    ProductController {

    @Autowired
    ProductServiceImpl productService;
    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody ProductRequestDTO productRequestDTO) throws SellerNotFound {
        try {
            ProductResponseDTO productResponseDTO = productService.addProduct(productRequestDTO);

            return new ResponseEntity(productResponseDTO, HttpStatus.CREATED);
        }
        catch (SellerNotFound e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/category/{category}/price/{price}")
    public ResponseEntity getProductByCategoryAndPrice(@PathVariable("category") Category category,@PathVariable("price") int price){
        List<ProductResponseDTO> productResponseDTOList = productService.getProductByCategoryAndPrice(category,price);

        return new ResponseEntity(productResponseDTOList,HttpStatus.FOUND);
    }


    // get top 5 cheapest product in category
    @GetMapping("/get/top5/cheapest/category/{category}")
    public ResponseEntity getTop5CheapestByCategory(@PathVariable("category") Category category){
        List<ProductResponseDTO> productResponseDTOList = productService.getTop5CheapestProductByCategory(category);

        return new ResponseEntity(productResponseDTOList,HttpStatus.FOUND);
    }
}
