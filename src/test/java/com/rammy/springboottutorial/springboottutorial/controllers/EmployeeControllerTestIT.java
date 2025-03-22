package com.rammy.springboottutorial.springboottutorial.controllers;

import com.rammy.springboottutorial.springboottutorial.TestContainerConfiguration;
import com.rammy.springboottutorial.springboottutorial.dto.EmployeeDTO;
import com.rammy.springboottutorial.springboottutorial.entities.EmployeeEntity;
import com.rammy.springboottutorial.springboottutorial.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
class EmployeeControllerTestIT {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EmployeeRepository employeeRepository;

    private EmployeeEntity employeeEntity;
    private EmployeeDTO testEmployeeDto;

    @BeforeEach
    void setup(){
        employeeEntity = EmployeeEntity.builder()
                .id(1L)
                .name("Ramesh Kumar")
                .email("ramesh@gmail.com")
                .age(23)
                .isActive(true)
                .build();
        testEmployeeDto = EmployeeDTO.builder()
                .id(1L)
                .name("Ramesh Kumar")
                .email("ramesh@gmail.com")
                .age(23)
                .isActive(true)
                .build();
    }

    @Test
    void testGetEmployeeById_success(){
        EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);
        webTestClient.get()
                .uri("/employee/{id}",savedEmployee.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDTO.class)
                .isEqualTo(testEmployeeDto);
//                .value(employeeDTO -> {
//                    assertThat(employeeDTO.getEmail()).isEqualTo(savedEmployee.getEmail());
//                    assertThat(employeeDTO.getId()).isEqualTo(savedEmployee.getId());
//                });
    }
}