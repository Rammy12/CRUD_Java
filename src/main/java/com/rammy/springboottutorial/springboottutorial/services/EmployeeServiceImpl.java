package com.rammy.springboottutorial.springboottutorial.services;

import com.rammy.springboottutorial.springboottutorial.dto.EmployeeDTO;
import com.rammy.springboottutorial.springboottutorial.entities.EmployeeEntity;
import com.rammy.springboottutorial.springboottutorial.exceptions.ResourceNotFoundException;
import com.rammy.springboottutorial.springboottutorial.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {
        log.info("Fetching employee with id: {}", employeeId);
        EmployeeEntity employee = findEmployeeById(employeeId);
        log.info("Successfully fetched employee with id: {}", employeeId);
        return modelMapper.map(employee,EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        log.info("Fetching employees from getAllEmployees");
        try {
            return employeeRepository.findAll()
                    .stream()
                    .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                    .collect(Collectors.toList());
        }catch (Exception e){
            log.error("Error while fetching employees: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch employees", e);
        }
    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee) {
        log.info("Creating new employee with email: {}",inputEmployee.getEmail());
        List<EmployeeEntity> existingEmployees = employeeRepository.findByEmail(inputEmployee.getEmail());
        if (!existingEmployees.isEmpty()) {
            log.error("Employee already exists with email: {}", inputEmployee.getEmail());
            throw new RuntimeException("Employee already exists with email: " + inputEmployee.getEmail());
        }
        EmployeeEntity newEmployee = modelMapper.map(inputEmployee, EmployeeEntity.class);
        EmployeeEntity savedEmployee = employeeRepository.save(newEmployee);
        log.info("Successfully created new employee with id: {}", savedEmployee.getId());
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> updates) {
        log.info("Partially updating Employee with id {}",employeeId);
        isExistsByEmployeeId(employeeId);
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        updates.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
        });
        log.info("Successfully partially updated employee with id: {}", employeeId);
        return modelMapper.map(employeeRepository.save(employeeEntity),EmployeeDTO.class);
    }

    @Override
    public boolean deleteEmployeeById(Long employeeId) {
        log.info("Deleting employee with id: {}", employeeId);
        isExistsByEmployeeId(employeeId);
        employeeRepository.deleteById(employeeId);
        log.info("Successfully deleted employee with id: {}", employeeId);
        return true;
    }

    @Override
    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO updatedEmployee) {
        log.info("Updating employee with id: {}", employeeId);
        EmployeeEntity employee = findEmployeeById(employeeId);
        if (!employee.getEmail().equals(updatedEmployee.getEmail())) {
            log.error("Attempted to update email for employee with id: {}", employeeId);
            throw new RuntimeException("The email of the employee cannot be updated");
        }
        EmployeeEntity employeeEntity = modelMapper.map(updatedEmployee, EmployeeEntity.class);
        employeeEntity.setId(employeeId);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        log.info("Successfully updated employee with id: {}", employeeId);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public void isExistsByEmployeeId(Long employeeId) {
        boolean exits=employeeRepository.existsById(employeeId);
        if (!exits) {
            log.error("Employee not found with id: {}", employeeId);
            throw new ResourceNotFoundException("Employee not found with id: " + employeeId);
        }
    }
    public EmployeeEntity findEmployeeById(Long id){
        return employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with id: {}", id);
                    return new ResourceNotFoundException("Employee not found with id: " + id);
                });
    }
}
