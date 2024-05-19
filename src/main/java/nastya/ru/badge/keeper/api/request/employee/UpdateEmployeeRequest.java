package nastya.ru.badge.keeper.api.request.employee;


import nastya.ru.badge.keeper.api.common.Position;

import java.util.UUID;

public class UpdateEmployeeRequest{
    private UUID id;
    private Position position;
    private String department;

    public UUID getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public String getDepartment() {
        return department;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}