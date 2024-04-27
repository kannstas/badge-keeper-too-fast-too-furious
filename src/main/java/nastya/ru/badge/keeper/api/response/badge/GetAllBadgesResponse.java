package nastya.ru.badge.keeper.api.response.badge;

import java.util.List;

public class GetAllBadgesResponse {
    private List<GetBadgeResponse> badges;

    public List<GetBadgeResponse> getBadges() {
        return badges;
    }
}
