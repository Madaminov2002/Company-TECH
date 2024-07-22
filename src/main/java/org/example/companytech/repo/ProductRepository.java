package org.example.companytech.repo;

import org.example.companytech.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Transactional
    @Modifying
    @Query("""
            update Product p set p.name = ?1, p.price = ?2, p.description = ?3, p.count = ?4, p.place = ?5
            where p.id = ?6""")
    int updateNameAndPriceAndDescriptionAndCountAndPlaceById(String name, BigDecimal price, String description, Integer count, String place, Long id);
}