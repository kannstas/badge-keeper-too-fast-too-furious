package nastya.ru.badge.keeper.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "badges")
public class Badge {
    @Id
    @Column(name = "id")
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "recipient_employee_id", referencedColumnName = "id")
    private Employee recipientEmployee;
    @ManyToOne()
    @JoinColumn(name="issuer_employee_id", referencedColumnName = "id")
    private Employee issuerEmployee;
    @Column(name="badge_serial_number")
    private String badgeSerialNumber;
    @Column(name="issuance_date")
    private Instant issuanceDate;
    @Column(name = "expiration_date")
    private Instant expirationDate;
    @Column(name = "active")
    private Boolean active;

    public Badge() {
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Employee getRecipientEmployee() {
        return recipientEmployee;
    }

    public void setRecipientEmployee(Employee recipientEmployee) {
        this.recipientEmployee = recipientEmployee;
    }

    public Employee getIssuerEmployee() {
        return issuerEmployee;
    }

    public void setIssuerEmployee(Employee issuerEmployee) {
        this.issuerEmployee = issuerEmployee;
    }

    public String getBadgeSerialNumber() {
        return badgeSerialNumber;
    }

    public void setBadgeSerialNumber(String badgeSerialNumber) {
        this.badgeSerialNumber = badgeSerialNumber;
    }

    public Instant getIssuanceDate() {
        return issuanceDate;
    }

    public void setIssuanceDate(Instant issuanceDate) {
        this.issuanceDate = issuanceDate;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Badge{" +
               "id=" + id +
               ", recipientEmployee=" + recipientEmployee +
               ", issuerEmployee=" + issuerEmployee +
               ", badgeSerialNumber='" + badgeSerialNumber + '\'' +
               ", issuanceDate=" + issuanceDate +
               ", expirationDate=" + expirationDate +
               ", active=" + active +
               '}';
    }
}