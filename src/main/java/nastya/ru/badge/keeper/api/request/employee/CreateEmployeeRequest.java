package nastya.ru.badge.keeper.api.request.employee;

import nastya.ru.badge.keeper.api.common.Position;

public class CreateEmployeeRequest{
    private Position position;
    private String department;

    public Position getPosition() {
        return position;
    }

    public String getDepartment() {
        return department;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}