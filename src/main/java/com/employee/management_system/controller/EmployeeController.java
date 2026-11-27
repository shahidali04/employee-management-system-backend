package com.employee.management_system.controller;

import com.employee.management_system.dto.EmployeeRequest;
import com.employee.management_system.dto.EmployeeResponse;
import com.employee.management_system.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    // Create employee
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeRequest request){

        EmployeeResponse response = employeeService.createEmployee(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    // Get all employees
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees(){

        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    // Search employee by name
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponse>> searchEmployees(
            @RequestParam String name
    ){

        return ResponseEntity.ok(employeeService.searchEmployees(name));
    }

    // Update employee
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request
    ){

        return ResponseEntity.ok(employeeService.updateEmployee(id, request));
    }

    // Delete employee
    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeResponse> deleteEmployee(
            @PathVariable Long id
    ){

        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }

    // Pagination and Sorting
    @GetMapping("/page")
    public ResponseEntity<Page<EmployeeResponse>> getEmployeesWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){

        return ResponseEntity.ok(
                employeeService.getEmployeesWithPagination(page, size, sortBy, direction)
        );
    }
}
