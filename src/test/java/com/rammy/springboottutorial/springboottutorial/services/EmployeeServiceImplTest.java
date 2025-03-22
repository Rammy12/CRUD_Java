package com.rammy.springboottutorial.springboottutorial.services;

import com.rammy.springboottutorial.springboottutorial.TestContainerConfiguration;
import com.rammy.springboottutorial.springboottutorial.dto.EmployeeDTO;
import com.rammy.springboottutorial.springboottutorial.entities.EmployeeEntity;
import com.rammy.springboottutorial.springboottutorial.exceptions.ResourceNotFoundException;
import com.rammy.springboottutorial.springboottutorial.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Import(TestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

//    @InjectMocks
//    private PostgreSQLContainer<?> postgreSQLContainer;
//
//    @Test
//    void testDatabaseConnection() {
//        assertThat(postgreSQLContainer.isRunning()).isTrue();
//    }

    private EmployeeEntity mockEmployee;
    private EmployeeDTO mockEmployeeDto;
    @BeforeEach
    void setup(){
        mockEmployee = EmployeeEntity.builder()
                .id(1L)
                .name("Ramesh Kumar")
                .email("ramesh@gmail.com")
                .age(23)
                .isActive(true)
                .build();
        mockEmployeeDto = modelMapper.map(mockEmployee, EmployeeDTO.class);
    }
    @Test
    void testGetEmployeeById_WhenEmployeeIdIsPresent_thanReturnEmployeeDTO(){
//        employeeService.getEmployeeById(1L);
        // assign
        Long id=mockEmployee.getId();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee));  // stubbing
        // act
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);
        // assert
        assertThat(employeeDTO).isNotNull();
        assertThat(employeeDTO.getId()).isEqualTo(id);
        assertThat(employeeDTO.getEmail()).isEqualTo(mockEmployee.getEmail());
        verify(employeeRepository,times(1)).findById(id);
        verify(employeeRepository,only()).findById(id);
    }

    @Test
    void testCreateNewEmployee_whenValidEmployeeThanCreateNewEmployee(){
        // assign
        when(employeeRepository.findByEmail(anyString())).thenReturn(List.of());
        when(employeeRepository.save(any(EmployeeEntity.class))).thenReturn(mockEmployee);
        // act
        EmployeeDTO employeeDTO = employeeService.createNewEmployee(mockEmployeeDto);
        // assert
        assertThat(employeeDTO).isNotNull();
        assertThat(employeeDTO.getEmail()).isEqualTo(mockEmployeeDto.getEmail());
        ArgumentCaptor<EmployeeEntity> employeeEntityArgumentCaptor = ArgumentCaptor.forClass(EmployeeEntity.class);
        verify(employeeRepository,times(1)).save(employeeEntityArgumentCaptor.capture());
        EmployeeEntity captureEmployee = employeeEntityArgumentCaptor.getValue();
        assertThat(captureEmployee.getEmail()).isEqualTo(mockEmployee.getEmail());
    }
    @Test
    void testGetEmployeeById_WhenEmployeeIdIsNotPresent_thanThrowException(){
        // assign
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        // act
        assertThatThrownBy(()->employeeService.getEmployeeById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: 1");
        verify(employeeRepository).findById(1L);
        // assert
    }
    @Test
    void testCreateNewEmployee_whenAttemptingToCreateEmployeeWithExitingEmail_throwException(){
        // arrange
        when(employeeRepository.findByEmail(anyString())).thenReturn(List.of(mockEmployee));

        // act
        // assert
        assertThatThrownBy(()->employeeService.createNewEmployee(mockEmployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee already exists with email: "+mockEmployee.getEmail());

        verify(employeeRepository).findByEmail(mockEmployeeDto.getEmail());
        verify(employeeRepository,never()).save(any());
    }

    @Test
    void testUpdateEmployeeById_whenEmployeeDoesNotExits_thrownException(){
        // arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(()->employeeService.updateEmployeeById(1L,mockEmployeeDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: 1");
        verify(employeeRepository).findById(1L);
        verify(employeeRepository,never()).save(any());
    }

    @Test
    void testUpdateEmployeeById_whenEmployeeAttemptingToUpdateEmail_thrownException(){
        when(employeeRepository.findById(mockEmployeeDto.getId())).thenReturn(Optional.of(mockEmployee));
        mockEmployeeDto.setName("Random");
        mockEmployeeDto.setEmail("random@gmail.com");
        // act and assert
        assertThatThrownBy(()->employeeService.updateEmployeeById(mockEmployeeDto.getId(),mockEmployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("The email of the employee cannot be updated");
        verify(employeeRepository).findById(1L);
        verify(employeeRepository,never()).save(any());
    }
    @Test
    void testUpdateEmployeeById_whenValidEmployee_thanUpdateEmployeeDTO(){
        // arrange
        when(employeeRepository.findById(mockEmployeeDto.getId())).thenReturn(Optional.of(mockEmployee));
        mockEmployeeDto.setName("Random");
        mockEmployeeDto.setIsActive(false);
        mockEmployeeDto.setAge(13);
        EmployeeEntity newEmployee = modelMapper.map(mockEmployeeDto,EmployeeEntity.class);
        when(employeeRepository.save(any(EmployeeEntity.class))).thenReturn(newEmployee);
        EmployeeDTO updatedEmployee = employeeService.updateEmployeeById(mockEmployeeDto.getId(),mockEmployeeDto);

        assertThat(updatedEmployee).isEqualTo(mockEmployeeDto);
        verify(employeeRepository).findById(mockEmployeeDto.getId());
        verify(employeeRepository).save(any());
    }
    @Test
    void testDeleteEmployeeById_whenEmployeeDoesNotExits_thrownException(){
        when(employeeRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(()->employeeService.deleteEmployeeById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: "+1L);
        verify(employeeRepository).existsById(1L);
        verify(employeeRepository,never()).deleteById(anyLong());
    }

    @Test
    void testDeleteEmployeeById_whenEmployeeIsValid_thanDeleteEmployee(){
        when(employeeRepository.existsById(1L)).thenReturn(true);
        assertThatCode(()->employeeService.deleteEmployeeById(1L))
                .doesNotThrowAnyException();
        verify(employeeRepository).deleteById(1L);
    }
}