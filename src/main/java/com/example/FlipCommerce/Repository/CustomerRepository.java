package com.example.FlipCommerce.Repository;

import com.example.FlipCommerce.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByEmailId(String emailId);

    @Query(value = "select * from customer c where c.age< :age",nativeQuery = true)
    List<Customer> findByAgeLessThan(int age);

    @Query(value = "select * from customer c where c.gender = 'FEMALE' AND c.age BETWEEN :fromAge AND :toAge", nativeQuery = true)
    List<Customer> getFemaleCustomerAgeBetween(int fromAge,int toAge);
}
