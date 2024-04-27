package nastya.ru.badge.keeper.entity;

import jakarta.persistence.*;
import nastya.ru.badge.keeper.api.common.Position;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    private Position position;
    @Column(name = "department")
    private String department;
    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "updated_at")
    private Instant updatedAt;
    @OneToOne(mappedBy = "employee")
    private EmployeePersonalData employeePersonalData;
    @OneToMany(mappedBy = "recipientEmployee")
    private List<Badge> badges;
    public Employee() {
    }

    public EmployeePersonalData getEmployeePersonalData() {
        return employeePersonalData;
    }

    public void setEmployeePersonalData(EmployeePersonalData employeePersonalData) {
        this.employeePersonalData = employeePersonalData;
    }

    public List<Badge> getBadges() {
        return badges;
    }

    public void setBadges(List<Badge> badges) {
        this.badges = badges;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

}
