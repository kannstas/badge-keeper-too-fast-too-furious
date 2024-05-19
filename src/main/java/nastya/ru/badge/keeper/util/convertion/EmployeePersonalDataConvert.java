package nastya.ru.badge.keeper.util.convertion;

import nastya.ru.badge.keeper.api.request.employee.personal.data.CreateEmployeePersonalDataRequest;
import nastya.ru.badge.keeper.api.response.employee.personal.data.GetEmployeePersonalDataResponse;
import nastya.ru.badge.keeper.entity.Employee;
import nastya.ru.badge.keeper.entity.EmployeePersonalData;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class EmployeePersonalDataConvert {
    private static final ModelMapper modelMapper = new ModelMapper();
    public static EmployeePersonalData toEmployeePersonalData(CreateEmployeePersonalDataRequest request) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        EmployeePersonalData employeePersonalData = modelMapper.map(request, EmployeePersonalData.class);
        Employee employee = new Employee();
        employee.setId(request.getEmployeeId());
        employeePersonalData.setEmployee(employee);

        return employeePersonalData;
    }

    public static GetEmployeePersonalDataResponse toEmployeePersonalDataResponse(EmployeePersonalData personalData) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        GetEmployeePersonalDataResponse personalDataResponse = modelMapper.map(personalData, GetEmployeePersonalDataResponse.class);
        personalDataResponse.setEmployeeId(personalData.getEmployee().getId());

        return personalDataResponse;
    }
}