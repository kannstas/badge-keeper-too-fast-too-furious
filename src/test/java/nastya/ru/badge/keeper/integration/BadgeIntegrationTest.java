package nastya.ru.badge.keeper.integration;

import nastya.ru.badge.keeper.api.common.Position;
import nastya.ru.badge.keeper.entity.Badge;
import nastya.ru.badge.keeper.entity.Employee;
import nastya.ru.badge.keeper.repository.BadgeRepository;
import nastya.ru.badge.keeper.repository.EmployeeRepository;
import nastya.ru.badge.keeper.service.BadgeService;
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
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Testcontainers
public class BadgeIntegrationTest {

    @Autowired
    private BadgeRepository badgeRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private BadgeService badgeService;

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.jpa.generate-ddl",() -> true);
    }

    @BeforeEach
    public void truncateCascade() {
        badgeRepository.deleteAll();
        employeeRepository.deleteAll();
    }
    @Test
    void testGetByIdBadgeSuccess() {

        Employee recipientEmployee = generateEmployee();
        Employee issueEmployee = generateEmployee();

        employeeRepository.save(recipientEmployee);
        employeeRepository.save(issueEmployee);

        Badge badge = generateBadge(recipientEmployee, issueEmployee);

        badgeRepository.save(badge);

        Badge foundBadge = badgeRepository.findById(badge.getId()).orElseThrow();

        assertThat(foundBadge.getId()).isEqualTo(badge.getId());
        assertThat(foundBadge.getIssuerEmployee().getId()).isEqualTo(badge.getIssuerEmployee().getId());
        assertThat(foundBadge.getRecipientEmployee().getId()).isEqualTo(badge.getRecipientEmployee().getId());

    }

    @Test
    void testGetAllBadgesSuccess() {

        Employee recipientEmployee = generateEmployee();
        Employee issueEmployee = generateEmployee();

        employeeRepository.save(recipientEmployee);
        employeeRepository.save(issueEmployee);

        Badge badge = generateBadge(recipientEmployee, issueEmployee);

        badgeRepository.save(badge);

        List<Badge> foundBadges =badgeRepository.findAll();

        assertEquals(1, foundBadges.size());
    }

    @Test
    void testSaveBadgeSuccess() {

        Employee recipientEmployee = generateEmployee();
        Employee issueEmployee = generateEmployee();

        employeeRepository.save(recipientEmployee);
        employeeRepository.save(issueEmployee);

        Badge badge = generateBadge(recipientEmployee, issueEmployee);

        badgeRepository.save(badge);

        Badge resultBadge = badgeRepository.findById(badge.getId()).orElseThrow();

        assertThat(resultBadge.getId()).isEqualTo(badge.getId());
        assertThat(resultBadge.getIssuerEmployee().getId()).isEqualTo(badge.getIssuerEmployee().getId());
        assertThat(resultBadge.getRecipientEmployee().getId()).isEqualTo(badge.getRecipientEmployee().getId());

    }

    @Test
    void testBadgeNotFoundSuccess() {

        //given
        employeeRepository.save(generateEmployee());

        //when
        Optional<Badge> actual = badgeRepository.findById(UUID.randomUUID());

        //then
        assertThat(actual).isEmpty();
    }

    @Test
    void testDisableSuccess() {
        Employee recipientEmployee = generateEmployee();
        Employee issueEmployee = generateEmployee();

        employeeRepository.save(recipientEmployee);
        employeeRepository.save(issueEmployee);

        Badge badge = generateBadge(recipientEmployee, issueEmployee);

        badgeRepository.save(badge);

        badgeService.disable(badge.getId());

        Badge foundBadge = badgeRepository.findById(badge.getId()).orElseThrow();
        boolean badgeIsDisable = foundBadge.getActive();

        assertFalse(badgeIsDisable);

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

    private Badge generateBadge( Employee recipientEmployee, Employee issueEmployee) {
        Badge badge = new Badge();
        badge.setId(UUID.randomUUID());
        badge.setRecipientEmployee(recipientEmployee);
        badge.setIssuerEmployee(issueEmployee);
        badge.setBadgeSerialNumber(randomAlphanumeric(10));
        badge.setIssuanceDate(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        badge.setExpirationDate(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        badge.setActive(true);
        return badge;
    }
}