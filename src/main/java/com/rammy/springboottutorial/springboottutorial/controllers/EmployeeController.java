package com.rammy.springboottutorial.springboottutorial.controllers;

import com.rammy.springboottutorial.springboottutorial.dto.EmployeeDTO;
import com.rammy.springboottutorial.springboottutorial.exceptions.ResourceNotFoundException;
import com.rammy.springboottutorial.springboottutorial.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping(path = "/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name ="employeeId") Long employeeId)
    {
        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid EmployeeDTO inputEmployee)
    {
        return new ResponseEntity<>(employeeService.createNewEmployee(inputEmployee),HttpStatus.CREATED);
    }


    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@Valid @RequestBody EmployeeDTO updatedEmployee,@PathVariable Long employeeId){
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId,updatedEmployee));
    }
    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId){
        boolean getDeleted= employeeService.deleteEmployeeById(employeeId);
        if (getDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@Valid @RequestBody Map<String,Object> updates,@PathVariable Long employeeId){
        EmployeeDTO employeeDTO=employeeService.updatePartialEmployeeById(employeeId,updates);
        if (employeeDTO==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }
}
