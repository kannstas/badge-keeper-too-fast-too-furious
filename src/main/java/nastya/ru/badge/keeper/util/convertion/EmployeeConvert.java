package nastya.ru.badge.keeper.util.convertion;

import nastya.ru.badge.keeper.api.request.employee.CreateEmployeeRequest;
import nastya.ru.badge.keeper.api.response.employee.GetEmployeeResponse;
import nastya.ru.badge.keeper.entity.Employee;
import org.modelmapper.ModelMapper;

public class EmployeeConvert {
    private static final ModelMapper modelMapper = new ModelMapper();
    public static GetEmployeeResponse toEmployeeResponse(Employee employee) {
        return modelMapper.map(employee, GetEmployeeResponse.class);
    }

    public static Employee toEmployee(CreateEmployeeRequest employeeRequest) {
        return modelMapper.map(employeeRequest, Employee.class);
    }
}