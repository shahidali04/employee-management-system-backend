package com.employee.management_system.service;

import com.employee.management_system.dto.EmployeeRequest;
import com.employee.management_system.dto.EmployeeResponse;
import com.employee.management_system.entity.Employee;
import com.employee.management_system.exception.DuplicateEmailException;
import com.employee.management_system.exception.EmployeeNotFoundException;
import com.employee.management_system.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    // Create employee
    public EmployeeResponse createEmployee(EmployeeRequest request){

        if (employeeRepository.existsByEmail(request.getEmail())){
            throw new DuplicateEmailException("Email already exists");
        }

        Employee employee = new Employee();

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setSalary(request.getSalary());
        employee.setMobileNumber(request.getMobileNumber());

        Employee savedEmployee = employeeRepository.save(employee);

        return convertToResponse(savedEmployee);
    }

    // Get employee by ID
    public EmployeeResponse getEmployeeById(Long id){

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found"));

        return convertToResponse(employee);
    }

    // Get all employees
    public List<EmployeeResponse> getAllEmployee(){
        return employeeRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Search employee by name
    public List<EmployeeResponse> searchEmployees(String name){
        return employeeRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Update employee
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request){

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found"));

        if (employeeRepository.existsByEmailAndIdNot(request.getEmail(), id)){
            throw new DuplicateEmailException("Email already exists");
        }

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setSalary(request.getSalary());
        employee.setMobileNumber(request.getMobileNumber());

        Employee updatedEmployee = employeeRepository.save(employee);

        return convertToResponse(updatedEmployee);
    }

    // Delete employee
    public void deleteEmployee(Long id){

        if (!employeeRepository.existsById(id)){
            throw new EmployeeNotFoundException("Employee not found");
        }

        employeeRepository.deleteById(id);
    }

    // Pagination and Sorting
    public Page<EmployeeResponse> getEmployeesWithPagination(
            int page, int size, String sortBy, String direction
    ){

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return employeeRepository.findAll(pageable)
                .map(this::convertToResponse);
    }

    // Convert Entity to Response DTO
    private EmployeeResponse convertToResponse(Employee employee){

        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getSalary(),
                employee.getMobileNumber()
        );
    }
}
