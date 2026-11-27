package com.employee.management_system.repository;

import com.employee.management_system.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByNameContainingIgnoreCase(String name);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
}
