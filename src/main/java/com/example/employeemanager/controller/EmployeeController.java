package com.example.employeemanager.controller;

import com.example.employeemanager.Service.EmployeeService;
import com.example.employeemanager.Service.UserNotFoundException;
import com.example.employeemanager.model.Employee;
import com.example.employeemanager.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    private final EmployeeService employeeService;
    @Autowired
//    private final EmployeeRepo employeeRepo;
    @GetMapping("/hello")
    public String hello(){
        return "hello ";
    }

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
//    public List<Employee> getAllEmployees() {
//        return employeeRepo.findAll();
//    }
    public ResponseEntity<List<Employee>> getAllEmployees () {
        List<Employee> employees = employeeService.findAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
    @GetMapping("/employees/{id}")
//    public Employee findEmployeeById(Long id) {
//        Optional<Employee> employeeOpt = employeeRepo.findEmployeeById(id);
//        Employee employee = employeeOpt.orElseThrow(() -> new UserNotFoundException("查無 user 資訊."));
////        return employeeRepo.findEmployeeById(id)
////                .orElseThrow(()-> new UserNotFoundException("not found id"));
//        return employee;
//    }
    public ResponseEntity<Employee> getEmployeeById (@PathVariable("id") Long id) {
        Employee employee = employeeService.findEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    @PostMapping("/employees")
//    public Employee addEmployee(@RequestBody Employee employee) {
//        employee.setEmployeeCode(UUID.randomUUID().toString());
//        return employeeRepo.save(employee);
//    }
    public ResponseEntity<Employee> addEmployee (@RequestBody Employee employee) {
        Employee newEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED); // 在 server 上創建東西
    }
    @PutMapping("/employees")
//    public Employee updateEmployee(@RequestBody Employee employee) {
//        return employeeRepo.save(employee);
//    }
    public ResponseEntity<Employee> updateEmployee (@RequestBody Employee employee) {
        Employee updateEmployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    @DeleteMapping("/employees/{id}")
//    public void deleteEmployee(@PathVariable("id") Long id) {
//        employeeRepo.deleteEmployeeById(id);
//    }
    public ResponseEntity<?> deleteEmployee (@PathVariable("id") Long id) {
        // 因為刪除不回傳任何東西不回傳所以打問號
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
