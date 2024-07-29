package org.example.companytech.repo;

import java.util.Optional;
import org.example.companytech.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Boolean existsByUsername(String username);
    Optional<Employee> findByUsername(String username);
    @Modifying
    @Transactional
    @Query(nativeQuery = true,value =
            "update employee_roles set roles_id=:rId where" +
            " employee_id= (select id from employee where username=:uName)"
    )
    void updateRoleEmployee(@Param("uName") String username, @Param("rId") Long roleId);
}