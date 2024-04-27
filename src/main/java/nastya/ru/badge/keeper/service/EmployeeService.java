package nastya.ru.badge.keeper.service;

import nastya.ru.badge.keeper.api.request.employee.CreateEmployeeRequest;
import nastya.ru.badge.keeper.api.request.employee.UpdateEmployeeRequest;
import nastya.ru.badge.keeper.api.response.employee.GetAllEmployeesResponse;
import nastya.ru.badge.keeper.api.response.employee.GetEmployeeResponse;
import nastya.ru.badge.keeper.entity.Employee;
import nastya.ru.badge.keeper.repository.EmployeeRepository;
import nastya.ru.badge.keeper.util.exception.IdNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public GetEmployeeResponse findById(UUID id) {
        return toEmployeeResponse(
                employeeRepository.findById(id).orElseThrow()
        );
    }

    public GetAllEmployeesResponse findAll() {
        List<GetEmployeeResponse> allEmployeesResponse = employeeRepository.findAll().stream()
                .map(this::toEmployeeResponse)
                .toList();

        return new GetAllEmployeesResponse(allEmployeesResponse);
    }

    @Transactional
    public void save(CreateEmployeeRequest employeeRequest) {
        Employee employee = toEmployee(employeeRequest);
        employee.setId(UUID.randomUUID());
        employee.setCreatedAt(Instant.now());
        employee.setUpdatedAt(Instant.now());

        employeeRepository.save(employee);
    }

    @Transactional
    public void update(UpdateEmployeeRequest employeeRequest) {
        Employee employee = employeeRepository.findById(employeeRequest.getId())
                .orElseThrow(() -> new IdNotFoundException("employee", employeeRequest.getId()));

        employee.setPosition(employeeRequest.getPosition());
        employee.setDepartment(employeeRequest.getDepartment());
    }

    @Transactional
    public void delete(UUID id){
        employeeRepository.deleteById(id);
    }

    private GetEmployeeResponse toEmployeeResponse(Employee employee) {
        return modelMapper.map(employee, GetEmployeeResponse.class);
    }

    private Employee toEmployee(CreateEmployeeRequest employeeRequest) {
        return modelMapper.map(employeeRequest, Employee.class);
    }
}