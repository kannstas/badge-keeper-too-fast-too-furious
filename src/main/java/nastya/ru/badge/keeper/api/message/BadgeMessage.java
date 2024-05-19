package nastya.ru.badge.keeper.api.message;

import java.util.UUID;

public class BadgeMessage {
    private UUID id;

    public BadgeMessage(UUID id) {
        this.id = id;
    }

    public BadgeMessage() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}