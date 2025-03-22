package com.rammy.springboottutorial.springboottutorial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rammy.springboottutorial.springboottutorial.entities.DepartmentEntity;
import jakarta.validation.constraints.*;
import lombok.*;


import java.time.LocalDate;
import java.util.Objects;

@Builder
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDTO that = (EmployeeDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getAge(), that.getAge()) && Objects.equals(getDateOfJoining(), that.getDateOfJoining()) && Objects.equals(getIsActive(), that.getIsActive()) && Objects.equals(getWorkerDepartment(), that.getWorkerDepartment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail(), getAge(), getDateOfJoining(), getIsActive(), getWorkerDepartment());
    }
}

