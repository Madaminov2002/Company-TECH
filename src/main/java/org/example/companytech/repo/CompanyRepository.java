package org.example.companytech.repo;

import java.util.Optional;
import javax.swing.text.StyledEditorKit;
import org.example.companytech.domain.Company;
import org.example.companytech.projection.CompanyDtoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);
    Optional<Company> findByUserName(String userName);
    Optional<Company> findByEmail(String email);
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update company set password=:password where email=:email")
    void changePassword(@Param("password") String password, @Param("email") String email);
    @Query(nativeQuery = true,value = "select company.user_name,company.password,company.email from company where email=:email")
    CompanyDtoProjection getChangedPasswordUser(@Param("email")String email);


}