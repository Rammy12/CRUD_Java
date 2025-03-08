package com.rammy.springboottutorial.springboottutorial.controllers;


import com.rammy.springboottutorial.springboottutorial.dto.DepartmentDTO;
import com.rammy.springboottutorial.springboottutorial.exceptions.ResourceNotFoundException;
import com.rammy.springboottutorial.springboottutorial.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    @GetMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long departmentId){
        return ResponseEntity.ok(departmentService.getDepartmentById(departmentId));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartment(){
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody @Valid DepartmentDTO newDepartment){
        return new ResponseEntity<>(departmentService.createNewDepartment(newDepartment), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{departmentId}")
    public ResponseEntity<Boolean> deleteDepartmentById(@PathVariable Long departmentId){
        boolean gotDeleted= departmentService.deleteDepartmentById(departmentId);
        if (gotDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @PutMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDTO> updateDepartmentById(@PathVariable Long departmentId
            ,@RequestBody @Valid DepartmentDTO toUpdateDepartment){
        return ResponseEntity.ok(departmentService.updateDepartmentById(departmentId,toUpdateDepartment));
    }

    @PutMapping(path = "/{departmentId}/manager/{employeeId}")
    public ResponseEntity<DepartmentDTO> assignManager(@PathVariable Long departmentId,
                                                       @PathVariable Long employeeId){
        return ResponseEntity.ok(departmentService.assignManager(departmentId,employeeId));
    }


    @PutMapping(path = "/{departmentId}/worker/{employeeId}")
    public ResponseEntity<DepartmentDTO> assignWorker(@PathVariable Long departmentId,
                                                       @PathVariable Long employeeId){
        return ResponseEntity.ok(departmentService.assignWorker(departmentId,employeeId));
    }
}
