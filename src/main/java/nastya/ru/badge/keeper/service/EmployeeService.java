package nastya.ru.badge.keeper.service;

import nastya.ru.badge.keeper.api.request.employee.CreateEmployeeRequest;
import nastya.ru.badge.keeper.api.request.employee.UpdateEmployeeRequest;
import nastya.ru.badge.keeper.api.response.employee.GetAllEmployeesResponse;
import nastya.ru.badge.keeper.api.response.employee.GetEmployeeResponse;
import nastya.ru.badge.keeper.entity.Employee;
import nastya.ru.badge.keeper.repository.EmployeeRepository;
import nastya.ru.badge.keeper.util.convertion.EmployeeConvert;
import nastya.ru.badge.keeper.util.exception.IdNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static nastya.ru.badge.keeper.util.convertion.EmployeeConvert.toEmployee;
import static nastya.ru.badge.keeper.util.convertion.EmployeeConvert.toEmployeeResponse;

@Service
@Transactional(readOnly = true)
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    Logger logger = Logger.getLogger(EmployeeService.class.getName());


    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public GetEmployeeResponse findById(UUID id) {
        logger.info("get employee by id: start: id = %s".formatted(id));
        return toEmployeeResponse(
                employeeRepository.findById(id).orElseThrow()
        );
    }

    public GetAllEmployeesResponse findAll() {
        logger.info("get all employees: start");
        List<GetEmployeeResponse> allEmployeesResponse = employeeRepository.findAll().stream()
                .map(EmployeeConvert::toEmployeeResponse)
                .toList();

        return new GetAllEmployeesResponse(allEmployeesResponse);
    }

    @Transactional
    public void save(CreateEmployeeRequest employeeRequest) {
        logger.info("save employee: start: request = %s".formatted(employeeRequest));
        Employee employee = toEmployee(employeeRequest);
        employee.setId(UUID.randomUUID());
        employee.setCreatedAt(Instant.now());
        employee.setUpdatedAt(Instant.now());

        employeeRepository.save(employee);
    }

    @Transactional
    public void update(UpdateEmployeeRequest employeeRequest) {
        logger.info("update employee: start: request = %s".formatted(employeeRequest));
        Employee employee = employeeRepository.findById(employeeRequest.getId())
                .orElseThrow(() -> new IdNotFoundException("employee", employeeRequest.getId()));

        employee.setPosition(employeeRequest.getPosition());
        employee.setDepartment(employeeRequest.getDepartment());
    }

    @Transactional
    public void delete(UUID id){
        logger.info("delete employee: start: id = %s".formatted(id));
        employeeRepository.deleteById(id);
    }

}