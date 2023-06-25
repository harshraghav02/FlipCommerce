package com.example.FlipCommerce.Controller;

import com.example.FlipCommerce.DTO.RequestDTO.SellerRequestDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.ProductResponseDTO;
import com.example.FlipCommerce.DTO.ResponseDTO.SellerResposeDTO;
import com.example.FlipCommerce.Enums.Category;
import com.example.FlipCommerce.Exception.SellerNotFound;
import com.example.FlipCommerce.Service.Impl.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerServiceImpl sellerService;
    @PostMapping("/add")
    public ResponseEntity addSeller(@RequestBody SellerRequestDTO sellerRequestDTO){
        SellerResposeDTO savedSeller = sellerService.addSeller(sellerRequestDTO);
        return new ResponseEntity(savedSeller, HttpStatus.CREATED);
    }

    //update seller by email id
    @PutMapping("/update-seller")
    public ResponseEntity updateSeller(@RequestParam String emailId,@RequestParam String name,@RequestParam String mobNo) throws SellerNotFound {

        try{
            SellerResposeDTO sellerResposeDTO = sellerService.updateSeller(emailId, name, mobNo);
            return new ResponseEntity(sellerResposeDTO, HttpStatus.FOUND);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    // get all the seller who sale product of perticular category
    @GetMapping("/get-seller/category/{category}")
    public ResponseEntity getAllSellerByCategory(@PathVariable("category")Category category){
        List<SellerResposeDTO> sellerResposeDTOS = sellerService.getAllSellerByCategory(category);
        return new ResponseEntity(sellerResposeDTOS,HttpStatus.FOUND);
    }
    //get all the product sold by seller in a category
    @GetMapping("/get/all-product-by-seller/category")
    public ResponseEntity getALlProductSoldBySellerInCategory(@RequestParam String sellerEmailId,@RequestParam Category category){
        try{
            List<ProductResponseDTO> productResponseDTOList = sellerService.getALlProductSoldBySellerInCategory(sellerEmailId,category);
            return new ResponseEntity(productResponseDTOList,HttpStatus.FOUND);
        } catch (SellerNotFound e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
    }


}
