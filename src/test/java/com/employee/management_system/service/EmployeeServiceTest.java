package com.employee.management_system.service;

import com.employee.management_system.dto.EmployeeRequest;
import com.employee.management_system.dto.EmployeeResponse;
import com.employee.management_system.entity.Employee;
import com.employee.management_system.exception.DuplicateEmailException;
import com.employee.management_system.exception.EmployeeNotFoundException;
import com.employee.management_system.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void testCreateEmployee(){

        EmployeeRequest request = new EmployeeRequest(
                "Rahul",
                "rahul@gmail.com",
                "IT",
                50000.0,
                "9876543210"
        );

        when(employeeRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(employeeRepository.save(any(Employee.class)))
                .thenAnswer(invocation -> {
                    Employee employee = invocation.getArgument(0);

                    employee.setId(1L);

                    return employee;
                });

        var response = employeeService.createEmployee(request);

        System.out.println(response);

        assertEquals(1L, response.getId());
        assertEquals("Rahul", response.getName());
        assertEquals("rahul@gmail.com", response.getEmail());

        verify(employeeRepository).save(any(Employee.class));
    }


    @Test
    void testDuplicateEmail(){

        EmployeeRequest request = new EmployeeRequest(
                "Rahul",
                "rahul@gmail.com",
                "IT",
                50000.0,
                "9876543210"
        );

        when(employeeRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);

        assertThrows(DuplicateEmailException.class, ()->
                employeeService.createEmployee(request));

        verify(employeeRepository, never()).save(any(Employee.class));
    }


    @Test
    void testEmployeeNotFound(){

        Long employeeId = 100L;

        when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, ()->
                employeeService.getEmployeeById(employeeId));
    }


    @Test
    void testGetEmployeeById() {

        Employee employee = new Employee(
                1L,
                "Rahul",
                "rahul@gmail.com",
                "IT",
                50000.0,
                "9876543210"
        );

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        EmployeeResponse response = employeeService.getEmployeeById(1L);

        assertEquals(1L, response.getId());
        assertEquals("Rahul", response.getName());
        assertEquals("rahul@gmail.com", response.getEmail());
    }


    @Test
    void testUpdateEmployee() {

        Employee employee = new Employee(
                1L,
                "Rahul",
                "rahul@gmail.com",
                "IT",
                50000.0,
                "9876543210"
        );

        EmployeeRequest request = new EmployeeRequest(
                "Rahul Sharma",
                "rahul@gmail.com",
                "Development",
                60000.0,
                "9876543210"
        );

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(employeeRepository.existsByEmailAndIdNot(
                request.getEmail(), 1L))
                .thenReturn(false);

        when(employeeRepository.save(any(Employee.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        EmployeeResponse response =
                employeeService.updateEmployee(1L, request);

        assertEquals("Rahul Sharma", response.getName());
        assertEquals("Development", response.getDepartment());
        assertEquals(60000.0, response.getSalary());
    }


    @Test
    void testDeleteEmployee(){

        Long employeeId = 1L;

        when(employeeRepository.existsById(employeeId))
                .thenReturn(true);

        employeeService.deleteEmployee(employeeId);

        verify(employeeRepository).deleteById(employeeId);
    }


    @Test
    void testGetAllEmployees() {

        Employee employee1 = new Employee(
                1L, "Rahul", "rahul@gmail.com",
                "IT", 50000.0, "9876543210"
        );

        Employee employee2 = new Employee(
                2L, "Amit", "amit@gmail.com",
                "HR", 40000.0, "9876543211"
        );

        when(employeeRepository.findAll())
                .thenReturn(List.of(employee1, employee2));

        List<EmployeeResponse> response =
                employeeService.getAllEmployee();

        assertEquals(2, response.size());
        assertEquals("Rahul", response.get(0).getName());
        assertEquals("Amit", response.get(1).getName());
    }


}