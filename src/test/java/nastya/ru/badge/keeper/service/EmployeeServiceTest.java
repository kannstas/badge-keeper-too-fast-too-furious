package nastya.ru.badge.keeper.service;

import nastya.ru.badge.keeper.api.common.Position;
import nastya.ru.badge.keeper.api.request.employee.CreateEmployeeRequest;
import nastya.ru.badge.keeper.api.request.employee.UpdateEmployeeRequest;
import nastya.ru.badge.keeper.entity.Employee;
import nastya.ru.badge.keeper.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void testSaveEmployeeSuccess() {
        CreateEmployeeRequest employeeRequest = new CreateEmployeeRequest();
        employeeRequest.setPosition(Position.MANAGER);
        employeeRequest.setDepartment(randomAlphanumeric(10));

        employeeService.save(employeeRequest);

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(captor.capture());

        Employee employeeToBeSaved = captor.getValue();

        assertThat(employeeToBeSaved.getDepartment()).isEqualTo(employeeRequest.getDepartment());
        assertThat(employeeToBeSaved.getDepartment()).isEqualTo(employeeRequest.getDepartment());
    }

    @Test
    void testUpdateEmployeeSuccess() {
        Employee employee = generateEmployee();

        UpdateEmployeeRequest updateEmployeeRequest = new UpdateEmployeeRequest();
        updateEmployeeRequest.setId(UUID.randomUUID());
        updateEmployeeRequest.setPosition(Position.MANAGER);
        updateEmployeeRequest.setDepartment(randomAlphanumeric(10));

        when(employeeRepository.findById(updateEmployeeRequest.getId()))
                .thenReturn(Optional.of(employee));

        employeeService.update(updateEmployeeRequest);

        verify(employeeRepository).findById(updateEmployeeRequest.getId());
    }

    @Test
    void testDeleteSuccess() {
        Employee employee = generateEmployee();
        employeeService.delete(employee.getId());

        verify(employeeRepository).deleteById(employee.getId());
    }

    private Employee generateEmployee() {
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setPosition(Position.SECURITY_OFFICER);
        employee.setDepartment("MAIN");
        employee.setCreatedAt(Instant.now());
        employee.setUpdatedAt(Instant.now());
        return employee;
    }
}