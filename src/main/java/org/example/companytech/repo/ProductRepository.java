package org.example.companytech.repo;

import java.math.BigDecimal;
import org.example.companytech.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Transactional
    @Modifying
    @Query("""
            update Product p set p.name = ?1, p.price = ?2, p.description = ?3, p.count = ?4, p.place = ?5
            where p.id = ?6""")
    int updateNameAndPriceAndDescriptionAndCountAndPlaceById(String name, BigDecimal price, String description, Integer count, String place, Long id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update product set count=count-:count where id=:id")
    void updateById(@Param("id") Long id, @Param("count") Integer count);
}