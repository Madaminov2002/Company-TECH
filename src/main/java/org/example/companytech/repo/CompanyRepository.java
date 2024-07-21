package org.example.companytech.repo;

import java.util.Optional;
import org.example.companytech.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Boolean existsByUserName(String userName);
    Optional<Company> findByUserName(String userName);
}