package com.rammy.springboottutorial.springboottutorial.services;

import com.rammy.springboottutorial.springboottutorial.dto.EmployeeDTO;

import java.util.List;
import java.util.Map;


public interface EmployeeService {
    EmployeeDTO getEmployeeById(Long employeeId);
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String,Object> updates);
    boolean deleteEmployeeById(Long employeeId);
    EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO updatedEmployee);
}
