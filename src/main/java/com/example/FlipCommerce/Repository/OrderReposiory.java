package com.example.FlipCommerce.Repository;

import com.example.FlipCommerce.Model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderReposiory extends JpaRepository<OrderEntity,Integer> {
}
