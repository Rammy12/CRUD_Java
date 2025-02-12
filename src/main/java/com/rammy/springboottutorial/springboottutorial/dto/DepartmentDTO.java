package com.rammy.springboottutorial.springboottutorial.dto;

import com.rammy.springboottutorial.springboottutorial.entities.EmployeeEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DepartmentDTO {
    private Long id;
    @NotBlank(message = "Title is required")
    private String title;
    private EmployeeEntity manager;
    private Set<EmployeeEntity> workers;
}
