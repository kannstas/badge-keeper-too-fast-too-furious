package nastya.ru.badge.keeper.integration;

import nastya.ru.badge.keeper.api.common.Position;
import nastya.ru.badge.keeper.entity.Employee;
import nastya.ru.badge.keeper.entity.EmployeePersonalData;
import nastya.ru.badge.keeper.repository.EmployeePersonalDataRepository;
import nastya.ru.badge.keeper.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
public class EmployeePersonalDataIntegrationTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeePersonalDataRepository employeePersonalDataRepository;
    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.jpa.generate-ddl", () -> true);
    }

    @BeforeEach
    public void truncateCascade() {
        employeePersonalDataRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void testGetEmployeePersonalDataByIdSuccess() {
        Employee employee = generateEmployee();
        employeeRepository.save(employee);

        EmployeePersonalData employeePersonalData = generateEmployeePersonalData(employee);
        employeePersonalDataRepository.save(employeePersonalData);

        EmployeePersonalData foundPersonalData = employeePersonalDataRepository.findEmployeePersonalDataByEmployeeId(employeePersonalData.getEmployee().getId()).orElseThrow();
        assertThat(foundPersonalData.getId()).isEqualTo(employeePersonalData.getId());
    }

    @Test
    void testEmployeePersonalDataGetAllSuccess() {
        Employee employee = generateEmployee();
        employeeRepository.save(employee);

        EmployeePersonalData employeePersonalData = generateEmployeePersonalData(employee);
        employeePersonalDataRepository.save(employeePersonalData);

        List<EmployeePersonalData> personalDataList = employeePersonalDataRepository.findAll();

        EmployeePersonalData foundPersonalData = personalDataList.stream()
                .findFirst()
                .orElseThrow();

        assertEquals(personalDataList.size(), 1);
        assertThat(foundPersonalData.getId()).isEqualTo(employeePersonalData.getId());
    }

    @Test
    void testEmployeePersonalDataSaveSuccess() {
        Employee employee = generateEmployee();
        employeeRepository.save(employee);

        EmployeePersonalData employeePersonalData = generateEmployeePersonalData(employee);
        employeePersonalDataRepository.save(employeePersonalData);

        EmployeePersonalData foundPersonalData = employeePersonalDataRepository.findEmployeePersonalDataByEmployeeId(employeePersonalData.getEmployee().getId()).orElseThrow();

        assertThat(foundPersonalData.getId()).isEqualTo(employeePersonalData.getId());
    }

    @Test
    void testEmployeePersonalDataDeleteSuccess() {
        Employee employee = generateEmployee();
        employeeRepository.save(employee);

        EmployeePersonalData employeePersonalData = generateEmployeePersonalData(employee);
        employeePersonalDataRepository.save(employeePersonalData);

        employeePersonalDataRepository.deleteById(employeePersonalData.getId());

        EmployeePersonalData foundPersonalData = employeePersonalDataRepository.findEmployeePersonalDataByEmployeeId(employeePersonalData.getEmployee().getId()).orElse(null);
        Employee foundEmployee = employeeRepository.findById(employee.getId()).orElseThrow();

        assertThat(foundPersonalData).isEqualTo(null);
        assertThat(foundEmployee.getId()).isEqualTo(employee.getId());

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

    public static EmployeePersonalData generateEmployeePersonalData(Employee employee) {
        EmployeePersonalData employeePersonalData = new EmployeePersonalData();
        employeePersonalData.setId(UUID.randomUUID());
        employeePersonalData.setEmployee(employee);
        employeePersonalData.setFirstName(randomAlphanumeric(10));
        employeePersonalData.setLastName(randomAlphanumeric(10));
        employeePersonalData.setMiddleName(randomAlphanumeric(10));
        employeePersonalData.setAge(20);
        employeePersonalData.setAddress(randomAlphanumeric(10));
        employeePersonalData.setPhoneNumber(randomAlphanumeric(10));
        employeePersonalData.setEmail(randomAlphanumeric(10));
        return employeePersonalData;
    }
}