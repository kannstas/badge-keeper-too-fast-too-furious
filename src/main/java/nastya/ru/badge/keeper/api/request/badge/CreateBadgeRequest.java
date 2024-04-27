package nastya.ru.badge.keeper.api.request.badge;

import java.util.UUID;

public class CreateBadgeRequest{
    private UUID recipientEmployeeId;
    private UUID issuerEmployeeId;
    private String badgeSerialNumber;

    public UUID getRecipientEmployeeId() {
        return recipientEmployeeId;
    }

    public UUID getIssuerEmployeeId() {
        return issuerEmployeeId;
    }

    public String getBadgeSerialNumber() {
        return badgeSerialNumber;
    }
}