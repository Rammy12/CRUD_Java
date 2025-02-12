package com.rammy.springboottutorial.springboottutorial.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rammy.springboottutorial.springboottutorial.entities.DepartmentEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;


    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Min(value = 18, message = "Age should be minimum 18")
    private Integer age;

    @FutureOrPresent(message = "Date of joining must be today or in the future")
    @NotNull(message = "Date of Joining is required")
    private LocalDate dateOfJoining;

    private Boolean isActive;
    private DepartmentEntity workerDepartment;

}

