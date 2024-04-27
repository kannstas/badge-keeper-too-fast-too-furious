package nastya.ru.badge.keeper.api.request.employee.personal.data;

import java.util.UUID;

public class UpdateEmployeePersonalDataRequest{
    private UUID id;
    private UUID employeeId;
    private String firstName;
    private String lastName;
    private String middleName;
    private Integer age;
    private String address;
    private String phoneNumber;
    private String email;

    public UUID getId() {
        return id;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Integer getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
