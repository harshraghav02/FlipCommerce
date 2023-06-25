package com.example.FlipCommerce.Repository;

import com.example.FlipCommerce.Enums.Category;
import com.example.FlipCommerce.Model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Integer> {
    Seller findByEmailId(String emailId);

    @Query(value = "select * from seller s where s.category = :category",nativeQuery = true)
    List<Seller> findAllByCategory(Category category);
}
