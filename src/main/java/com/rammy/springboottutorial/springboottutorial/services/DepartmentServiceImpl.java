package com.rammy.springboottutorial.springboottutorial.services;

import com.rammy.springboottutorial.springboottutorial.dto.DepartmentDTO;
import com.rammy.springboottutorial.springboottutorial.entities.DepartmentEntity;
import com.rammy.springboottutorial.springboottutorial.entities.EmployeeEntity;
import com.rammy.springboottutorial.springboottutorial.exceptions.ResourceNotFoundException;
import com.rammy.springboottutorial.springboottutorial.repositories.DepartmentRepository;
import com.rammy.springboottutorial.springboottutorial.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Optional<DepartmentDTO> getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId).map(departmentEntity -> modelMapper.map(departmentEntity,DepartmentDTO.class));
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository
                .findAll()
                .stream()
                .map(departmentEntity -> modelMapper.map(departmentEntity,DepartmentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO createNewDepartment(DepartmentDTO newDepartment) {
        DepartmentEntity departmentToSave = modelMapper.map(newDepartment,DepartmentEntity.class);
        return modelMapper.map(departmentRepository.save(departmentToSave),DepartmentDTO.class);
    }

    @Override
    public boolean deleteDepartmentById(Long departmentId) {
        isExistsByDepartmentId(departmentId);
        departmentRepository.deleteById(departmentId);
        return true;
    }

    @Override
    public DepartmentDTO updateDepartmentById(Long departmentId, DepartmentDTO updatedDepartment) {
        isExistsByDepartmentId(departmentId);
        DepartmentEntity departmentEntity=modelMapper.map(updatedDepartment,DepartmentEntity.class);
        departmentEntity.setId(departmentId);
        DepartmentEntity toSaveDepartment=departmentRepository.save(departmentEntity);
        return modelMapper.map(toSaveDepartment,DepartmentDTO.class);
    }

    @Override
    public DepartmentDTO assignManager(Long departmentId, Long employeeId) {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(departmentId);
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);
        return departmentEntity.flatMap(department ->
                employeeEntity.map(employee -> {
                    department.setManager(employee);
                    return modelMapper.map(departmentRepository.save(department), DepartmentDTO.class);
                })).orElseThrow(()->new ResourceNotFoundException("Department not found with id: " + departmentId));
    }

    @Override
    public DepartmentDTO assignWorker(Long departmentId, Long employeeId) {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(departmentId);
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);
        return departmentEntity.flatMap(department ->
                employeeEntity.map(employee -> {
                    employee.setWorkerDepartment(department);
                    employeeRepository.save(employee);
                    department.getWorkers().add(employee);
                    return modelMapper.map(department, DepartmentDTO.class);
                })).orElseThrow(()->new ResourceNotFoundException("Department not found with id: " + departmentId));
    }

    public void isExistsByDepartmentId(Long departmentId) {
        boolean exits = departmentRepository.existsById(departmentId);
        if (!exits) throw new ResourceNotFoundException("Employee not found with id: " + departmentId);
    }
    public void isExistsByEmployeeId(Long employeeId) {
        boolean exits=employeeRepository.existsById(employeeId);
        if (!exits) throw new ResourceNotFoundException("Employee not found with id: "+employeeId);
    }
}
