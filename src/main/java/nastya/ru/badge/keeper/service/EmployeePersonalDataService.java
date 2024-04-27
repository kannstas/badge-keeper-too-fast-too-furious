package nastya.ru.badge.keeper.service;

import nastya.ru.badge.keeper.api.request.employee.personal.data.CreateEmployeePersonalDataRequest;
import nastya.ru.badge.keeper.api.request.employee.personal.data.UpdateEmployeePersonalDataRequest;
import nastya.ru.badge.keeper.api.response.employee.personal.data.GetAllEmployeesPersonalDataResponse;
import nastya.ru.badge.keeper.api.response.employee.personal.data.GetEmployeePersonalDataResponse;
import nastya.ru.badge.keeper.entity.Employee;
import nastya.ru.badge.keeper.entity.EmployeePersonalData;
import nastya.ru.badge.keeper.repository.EmployeePersonalDataRepository;
import nastya.ru.badge.keeper.repository.EmployeeRepository;
import nastya.ru.badge.keeper.util.exception.IdNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class EmployeePersonalDataService {
    private EmployeePersonalDataRepository employeePersonalDataRepository;
    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;

    public EmployeePersonalDataService(EmployeePersonalDataRepository employeePersonalDataRepository, EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeePersonalDataRepository = employeePersonalDataRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public GetEmployeePersonalDataResponse findByEmployeeId(UUID employeeId) {
        return toEmployeePersonalDataResponse(
                employeePersonalDataRepository.findEmployeePersonalDataByEmployeeId(employeeId)
                        .orElseThrow(() -> new IdNotFoundException("employee", employeeId))
        );
    }

    public GetAllEmployeesPersonalDataResponse findAll() {
        List<GetEmployeePersonalDataResponse> personalDataList = employeePersonalDataRepository.findAll()
                .stream().map(this::toEmployeePersonalDataResponse)
                .toList();

        return new GetAllEmployeesPersonalDataResponse(personalDataList);
    }

    @Transactional
    public void save(CreateEmployeePersonalDataRequest request) {
        employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new IdNotFoundException("employee", request.getEmployeeId()));
        EmployeePersonalData personalData =  toEmployeePersonalData(request);
        personalData.setId(UUID.randomUUID());

        employeePersonalDataRepository.save(personalData);
    }

    @Transactional
    public void update(UpdateEmployeePersonalDataRequest request) {
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
        employeePersonalDataRepository.deleteById(id);
    }

    private EmployeePersonalData toEmployeePersonalData(CreateEmployeePersonalDataRequest request) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        EmployeePersonalData employeePersonalData = modelMapper.map(request, EmployeePersonalData.class);
        Employee employee = new Employee();
        employee.setId(request.getEmployeeId());
        employeePersonalData.setEmployee(employee);

        return employeePersonalData;
    }

    private GetEmployeePersonalDataResponse toEmployeePersonalDataResponse(EmployeePersonalData personalData) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        GetEmployeePersonalDataResponse personalDataResponse = modelMapper.map(personalData, GetEmployeePersonalDataResponse.class);
        personalDataResponse.setEmployeeId(personalData.getEmployee().getId());

        return personalDataResponse;
    }
}