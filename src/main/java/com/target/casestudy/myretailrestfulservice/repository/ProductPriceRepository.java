package com.target.casestudy.myretailrestfulservice.repository;

import com.target.casestudy.myretailrestfulservice.model.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Integer> {
    ProductPrice findById(int id);
}
