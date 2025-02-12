package com.rammy.springboottutorial.springboottutorial.services;

import com.rammy.springboottutorial.springboottutorial.dto.EmployeeDTO;
import com.rammy.springboottutorial.springboottutorial.entities.EmployeeEntity;
import com.rammy.springboottutorial.springboottutorial.exceptions.ResourceNotFoundException;
import com.rammy.springboottutorial.springboottutorial.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<EmployeeDTO> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId).map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class));
    }

    @Override
    public List<EmployeeDTO> getAllEmployyes() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee) {
        EmployeeEntity employeeEntityToSave = modelMapper.map(inputEmployee, EmployeeEntity.class);
        return modelMapper.map(employeeRepository.save(employeeEntityToSave), EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> updates) {
        isExistsByEmployeeId(employeeId);
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        updates.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
        });
        return modelMapper.map(employeeRepository.save(employeeEntity),EmployeeDTO.class);
    }

    @Override
    public boolean deleteEmployeeById(Long employeeId) {
        isExistsByEmployeeId(employeeId);
        employeeRepository.deleteById(employeeId);
        return true;
    }

    @Override
    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO updatedEmployee) {
        isExistsByEmployeeId(employeeId);
        EmployeeEntity employeeEntity = modelMapper.map(updatedEmployee, EmployeeEntity.class);
        employeeEntity.setId(employeeId);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public void isExistsByEmployeeId(Long employeeId) {
        boolean exits=employeeRepository.existsById(employeeId);
        if (!exits) throw new ResourceNotFoundException("Employee not found with id: "+employeeId);
    }
}
