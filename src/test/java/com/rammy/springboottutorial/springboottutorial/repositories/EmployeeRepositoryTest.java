package com.rammy.springboottutorial.springboottutorial.repositories;

import com.rammy.springboottutorial.springboottutorial.entities.EmployeeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    private EmployeeEntity employeeEntity;

    @BeforeEach
    void setup(){
        employeeEntity = EmployeeEntity.builder()
                .id(1L)
                .name("Ramesh Kumar")
                .email("ramesh@gmail.com")
                .age(23)
                .isActive(true)
                .build();
    }

    @Test
    void testFindByEmail_whenEmailIsValid_thanReturnEmployee() {
        // Arrange
        employeeRepository.save(employeeEntity);
        // Act
        List<EmployeeEntity>employees= employeeRepository.findByEmail(employeeEntity.getEmail());
        // Assert
        assertThat(employees).isNotNull();
        assertThat(employees).isNotEmpty();
        assertThat(employees.get(0).getEmail()).isEqualTo(employeeEntity.getEmail());

    }

    @Test
    void testFindByEmail_whenEmailIsNotFound_thanReturnEmployeeEmptyList() {
        // Arrange
        String email = "ramesh123@gmail.com";
        // when
        List<EmployeeEntity> employeeEntities = employeeRepository.findByEmail(email);
        // then
        assertThat(employeeEntities).isNotNull();
        assertThat(employeeEntities).isEmpty();
    }
}