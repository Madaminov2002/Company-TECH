package org.example.companytech.repo;

import org.example.companytech.domain.ForgotPassword;
import org.example.companytech.projection.ForgotPasswordProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {
    @Query(nativeQuery = true, value = "select * from forgot_password where email=:email ")
    ForgotPasswordProjection findForgotPasswordByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update forgot_password set enabled=true where email=:email")
    void updateEnabledToTrue(@Param("email") String email);

    @Query(nativeQuery = true, value = "select forgot_password.enabled from forgot_password where enabled=true and email=:email")
    public Boolean checkByEmailEnabled(@Param("email") String email);
}