package nastya.ru.badge.keeper.api.response.employee.personal.data;

import java.util.List;

public class GetAllEmployeesPersonalDataResponse {
    private List<GetEmployeePersonalDataResponse> employeesPersonalData;

    public GetAllEmployeesPersonalDataResponse(List<GetEmployeePersonalDataResponse> employeesPersonalData) {
        this.employeesPersonalData = employeesPersonalData;
    }

    public List<GetEmployeePersonalDataResponse> getEmployeesPersonalData() {
        return employeesPersonalData;
    }
}