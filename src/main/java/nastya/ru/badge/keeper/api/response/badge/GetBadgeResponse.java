package nastya.ru.badge.keeper.api.response.badge;


import lombok.Data;

import java.time.Instant;
import java.util.UUID;
@Data
public class GetBadgeResponse {
    private UUID id;
    private UUID recipientEmployeeId;
    private UUID issuerEmployeeId; // TODO в маппере преобразовать сущности в их id
    private String badgeSerialNumber;
    private Instant issuanceDate;
    private Instant expirationDate;
    private Boolean active;

    public UUID getId() {
        return id;
    }

    public UUID getRecipientEmployeeId() {
        return recipientEmployeeId;
    }

    public UUID getIssuerEmployeeId() {
        return issuerEmployeeId;
    }

    public String getBadgeSerialNumber() {
        return badgeSerialNumber;
    }

    public Instant getIssuanceDate() {
        return issuanceDate;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setRecipientEmployeeId(UUID recipientEmployeeId) {
        this.recipientEmployeeId = recipientEmployeeId;
    }

    public void setIssuerEmployeeId(UUID issuerEmployeeId) {
        this.issuerEmployeeId = issuerEmployeeId;
    }

    public void setBadgeSerialNumber(String badgeSerialNumber) {
        this.badgeSerialNumber = badgeSerialNumber;
    }

    public void setIssuanceDate(Instant issuanceDate) {
        this.issuanceDate = issuanceDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
