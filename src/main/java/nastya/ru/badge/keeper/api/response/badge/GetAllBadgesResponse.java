package nastya.ru.badge.keeper.api.response.badge;

import java.util.List;

public class GetAllBadgesResponse {
    private List<GetBadgeResponse> badges;

    public GetAllBadgesResponse(List<GetBadgeResponse> badges) {
        this.badges = badges;
    }

    public List<GetBadgeResponse> getBadges() {
        return badges;
    }
}
