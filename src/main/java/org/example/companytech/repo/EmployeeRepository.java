package org.example.companytech.repo;

import java.util.Optional;
import org.example.companytech.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Boolean existsByUsername(String username);
    Optional<Employee> findByUsername(String username);
}