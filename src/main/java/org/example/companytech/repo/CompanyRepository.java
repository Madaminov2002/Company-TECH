package org.example.companytech.repo;

import java.util.Optional;
import javax.swing.text.StyledEditorKit;
import org.example.companytech.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);
    Optional<Company> findByUserName(String userName);
}