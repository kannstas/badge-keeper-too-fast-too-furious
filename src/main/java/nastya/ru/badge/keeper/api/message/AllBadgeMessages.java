package nastya.ru.badge.keeper.api.message;

import java.util.List;

public class AllBadgeMessages {
    private List<BadgeMessage> badgeMessages;

    public AllBadgeMessages(List<BadgeMessage> badgeMessages) {
        this.badgeMessages = badgeMessages;
    }

    public AllBadgeMessages() {
    }

    public List<BadgeMessage> getBadgeMessages() {
        return badgeMessages;
    }

    public void setBadgeMessages(List<BadgeMessage> badgeMessages) {
        this.badgeMessages = badgeMessages;
    }
}