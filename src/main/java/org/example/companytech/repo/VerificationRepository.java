package org.example.companytech.repo;

import org.example.companytech.domain.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Long> {
    Verification findByEmail(String email);
}