package nastya.ru.badge.keeper.service;

import nastya.ru.badge.keeper.api.request.employee.personal.data.CreateEmployeePersonalDataRequest;
import nastya.ru.badge.keeper.api.request.employee.personal.data.UpdateEmployeePersonalDataRequest;
import nastya.ru.badge.keeper.api.response.employee.personal.data.GetAllEmployeesPersonalDataResponse;
import nastya.ru.badge.keeper.api.response.employee.personal.data.GetEmployeePersonalDataResponse;
import nastya.ru.badge.keeper.entity.Employee;
import nastya.ru.badge.keeper.entity.EmployeePersonalData;
import nastya.ru.badge.keeper.repository.EmployeePersonalDataRepository;
import nastya.ru.badge.keeper.repository.EmployeeRepository;
import nastya.ru.badge.keeper.util.convertion.EmployeePersonalDataConvert;
import nastya.ru.badge.keeper.util.exception.IdNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static nastya.ru.badge.keeper.util.convertion.EmployeePersonalDataConvert.toEmployeePersonalData;
import static nastya.ru.badge.keeper.util.convertion.EmployeePersonalDataConvert.toEmployeePersonalDataResponse;

@Service
@Transactional(readOnly = true)
public class EmployeePersonalDataService {
    private EmployeePersonalDataRepository employeePersonalDataRepository;
    private EmployeeRepository employeeRepository;
    Logger logger = Logger.getLogger(EmployeePersonalDataService.class.getName());

    public EmployeePersonalDataService(EmployeePersonalDataRepository employeePersonalDataRepository, EmployeeRepository employeeRepository) {
        this.employeePersonalDataRepository = employeePersonalDataRepository;
        this.employeeRepository = employeeRepository;
    }

    public GetEmployeePersonalDataResponse findByEmployeeId(UUID employeeId) {
        logger.info("get personal data by employeeId: start: id = %s".formatted(employeeId));

        return toEmployeePersonalDataResponse(
                employeePersonalDataRepository.findEmployeePersonalDataByEmployeeId(employeeId)
                        .orElseThrow(() -> new IdNotFoundException("employee", employeeId))
        );
    }

    public GetAllEmployeesPersonalDataResponse findAll() {
        logger.info("get all personal data start:");

        List<GetEmployeePersonalDataResponse> personalDataList = employeePersonalDataRepository.findAll()
                .stream().map(EmployeePersonalDataConvert::toEmployeePersonalDataResponse)
                .toList();

        return new GetAllEmployeesPersonalDataResponse(personalDataList);
    }

    @Transactional
    public void save(CreateEmployeePersonalDataRequest request) {
        logger.info("save employeePersonalData: start: request = %s".formatted(request));

        employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new IdNotFoundException("employee", request.getEmployeeId()));
        EmployeePersonalData personalData =  toEmployeePersonalData(request);
        personalData.setId(UUID.randomUUID());

        employeePersonalDataRepository.save(personalData);
    }

    @Transactional
    public void update(UpdateEmployeePersonalDataRequest request) {
        logger.info("update employeePersonalData: start: request = %s".formatted(request));

        EmployeePersonalData personalData = employeePersonalDataRepository.findEmployeePersonalDataByEmployeeId(request.getEmployeeId())
                .orElseThrow(() -> new IdNotFoundException("employee", request.getEmployeeId()));
        Employee employee = new Employee();
        employee.setId(request.getEmployeeId());

        personalData.setEmployee(employee);
        personalData.setFirstName(request.getFirstName());
        personalData.setLastName(request.getLastName());
        personalData.setMiddleName(request.getMiddleName());
        personalData.setAge(request.getAge());
        personalData.setAddress(request.getAddress());
        personalData.setPhoneNumber(request.getPhoneNumber());
        personalData.setEmail(request.getEmail());
    }

    @Transactional
    public void delete(UUID id) {
        logger.info("get by employeeId: start: id = %s".formatted(id));

        employeePersonalDataRepository.deleteById(id);
    }
}