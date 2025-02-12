package com.rammy.springboottutorial.springboottutorial.services;

import com.rammy.springboottutorial.springboottutorial.dto.EmployeeDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeService {
    Optional<EmployeeDTO> getEmployeeById(Long employeeId);
    List<EmployeeDTO> getAllEmployyes();
    EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String,Object> updates);
    boolean deleteEmployeeById(Long employeeId);
    EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO updatedEmployee);
}
