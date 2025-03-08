package com.rammy.springboottutorial.springboottutorial.services;

import com.rammy.springboottutorial.springboottutorial.dto.DepartmentDTO;
import com.rammy.springboottutorial.springboottutorial.dto.EmployeeDTO;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    DepartmentDTO getDepartmentById(Long departmentId);
    List<DepartmentDTO> getAllDepartments();
    DepartmentDTO createNewDepartment(DepartmentDTO newDepartment);
    boolean deleteDepartmentById(Long departmentId);
    DepartmentDTO updateDepartmentById(Long departmentId, DepartmentDTO updatedDepartment);
    DepartmentDTO assignManager(Long departmentId,Long employeeId);
    DepartmentDTO assignWorker(Long departmentId,Long employeeId);
}
