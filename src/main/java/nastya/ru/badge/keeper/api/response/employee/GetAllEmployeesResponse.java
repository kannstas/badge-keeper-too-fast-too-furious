package nastya.ru.badge.keeper.api.response.employee;

import java.util.List;

public class GetAllEmployeesResponse {
    private List<GetEmployeeResponse> employees;

    public GetAllEmployeesResponse(List<GetEmployeeResponse> employees) {
        this.employees = employees;
    }

    public List<GetEmployeeResponse> getEmployees() {
        return employees;
    }
}

