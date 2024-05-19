package nastya.ru.badge.keeper.util.convertion;

import nastya.ru.badge.keeper.api.message.BadgeMessage;
import nastya.ru.badge.keeper.api.request.badge.CreateBadgeRequest;
import nastya.ru.badge.keeper.api.response.badge.GetBadgeResponse;
import nastya.ru.badge.keeper.entity.Badge;
import nastya.ru.badge.keeper.entity.Employee;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class BadgeConvert {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static Badge toBadge(CreateBadgeRequest badgeRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Badge badge = modelMapper.map(badgeRequest, Badge.class);
        Employee recipientEmployee = new Employee();
        recipientEmployee.setId(badgeRequest.getRecipientEmployeeId());
        badge.setRecipientEmployee(recipientEmployee);

        Employee issuerEmployee = new Employee();
        issuerEmployee.setId(badgeRequest.getIssuerEmployeeId());
        badge.setIssuerEmployee(issuerEmployee);

        return badge;
    }

    public static GetBadgeResponse toBadgeResponse(Badge badge) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        GetBadgeResponse badgeResponse = modelMapper.map(badge, GetBadgeResponse.class);

        badgeResponse.setRecipientEmployeeId(badge.getRecipientEmployee().getId());
        badgeResponse.setIssuerEmployeeId(badge.getIssuerEmployee().getId());

        return badgeResponse;
    }

    public static BadgeMessage toBadgeMessage(Badge badge) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(badge, BadgeMessage.class);
    }
}