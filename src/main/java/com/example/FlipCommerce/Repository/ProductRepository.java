package com.example.FlipCommerce.Repository;

import com.example.FlipCommerce.Enums.Category;
import com.example.FlipCommerce.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByCategoryAndPrice(Category category,int price);

    @Query(value = "select * from product p where p.category = :category order by p.price ASC limit 5",nativeQuery = true)
    List<Product> getTop5ProductCheapestByCategory(String category);
}
