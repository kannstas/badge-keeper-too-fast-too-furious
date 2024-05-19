package nastya.ru.badge.keeper.service;

import nastya.ru.badge.keeper.api.message.AllBadgeMessages;
import nastya.ru.badge.keeper.api.message.BadgeMessage;
import nastya.ru.badge.keeper.api.response.badge.GetAllBadgesResponse;
import nastya.ru.badge.keeper.api.response.badge.GetBadgeResponse;
import nastya.ru.badge.keeper.entity.Badge;
import nastya.ru.badge.keeper.entity.Employee;
import nastya.ru.badge.keeper.repository.BadgeRepository;
import nastya.ru.badge.keeper.util.exception.IdNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BadgeServiceTest {

    @Mock
    private BadgeRepository badgeRepository;

    @InjectMocks
    private BadgeService badgeService;
    @Test
    void testFindBadgeByIdSuccess() {

        Badge badge = generateBadge();

        GetBadgeResponse expectedResponse = new GetBadgeResponse();
        expectedResponse.setId(badge.getId());
        expectedResponse.setRecipientEmployeeId(badge.getRecipientEmployee().getId());
        expectedResponse.setIssuerEmployeeId(badge.getIssuerEmployee().getId());
        expectedResponse.setBadgeSerialNumber(badge.getBadgeSerialNumber());
        expectedResponse.setIssuanceDate(badge.getIssuanceDate());
        expectedResponse.setExpirationDate(badge.getExpirationDate());
        expectedResponse.setActive(badge.getActive());

        when(badgeRepository.findById(badge.getId()))
                .thenReturn(Optional.of(badge));

        GetBadgeResponse result = badgeService.findById(badge.getId());

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    void findBadgeByIdNotFoundShouldThrow(){
        Badge badge = generateBadge();

        when(badgeRepository.findById(badge.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> badgeService.findById(badge.getId()))
                .isInstanceOf(IdNotFoundException.class)
                .hasMessageContaining("В базе данных нет %s с id=%s".formatted("badge", badge.getId()));
    }
    @Test
    void findAllBadgesSuccess() {

        Badge badge1 = generateBadge();
        Badge badge2 = generateBadge();
        List<Badge> badges = Arrays.asList(badge1, badge2);
        List<GetBadgeResponse> expectedBadgeResponse = getBadgeResponses(badge1, badge2);


        when(badgeRepository.findAll()).thenReturn(badges);

        GetAllBadgesResponse response = badgeService.findAll();
        GetAllBadgesResponse expectedResponse = new GetAllBadgesResponse(expectedBadgeResponse);

        assertThat(expectedResponse)
                .usingRecursiveComparison()
                .isEqualTo(response);

    }


    @Test
    void findAllBadgeMessageWhereActiveIsTrue() {
        Badge badge1 = generateBadge();
        Badge badge2 = generateBadge();

        List<Badge> badges = Arrays.asList(badge1, badge2);

        when(badgeRepository.findBadgeByActiveIsTrue()).thenReturn(badges);

        BadgeMessage badgeMessage1 = new BadgeMessage(badge1.getId());
        BadgeMessage badgeMessage2 = new BadgeMessage(badge2.getId());

        AllBadgeMessages expectedMessage = new AllBadgeMessages(Arrays.asList(badgeMessage1, badgeMessage2));
        AllBadgeMessages result = badgeService.findAllBadgeMessageWhereActiveIsTrue();

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedMessage);
    }

    @Test
    void disable() {
        Badge badge = generateBadge();

        when(badgeRepository.findById(badge.getId()))
                .thenReturn(Optional.of(badge));

        badgeService.disable(badge.getId());

       verify(badgeRepository, times(1)).findById(badge.getId());
       assertFalse(badge.getActive());

    }

    private static Badge generateBadge() {
        Badge badge = new Badge();
        badge.setId(UUID.randomUUID());
        badge.setRecipientEmployee(generateEmployee());
        badge.setIssuerEmployee(generateEmployee());
        badge.setBadgeSerialNumber( randomAlphanumeric(10));
        badge.setIssuanceDate( Instant.now().truncatedTo(ChronoUnit.MILLIS));
        badge.setExpirationDate( Instant.now().truncatedTo(ChronoUnit.MILLIS));
        badge.setActive(true);
        return badge;
    }

    private static Employee generateEmployee() {
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        return employee;
    }

    private static List<GetBadgeResponse> getBadgeResponses(Badge badge1, Badge badge2) {
        GetBadgeResponse badgeResponse1 = getGetBadgeResponse(badge1);
        GetBadgeResponse badgeResponse2 = getGetBadgeResponse(badge2);

        return Arrays.asList(badgeResponse1, badgeResponse2);
    }

    private static GetBadgeResponse getGetBadgeResponse(Badge badge) {
        GetBadgeResponse badgeResponse = new GetBadgeResponse();
        badgeResponse.setId(badge.getId());
        badgeResponse.setRecipientEmployeeId(badge.getRecipientEmployee().getId());
        badgeResponse.setIssuerEmployeeId(badge.getIssuerEmployee().getId());
        badgeResponse.setBadgeSerialNumber(badge.getBadgeSerialNumber());
        badgeResponse.setIssuanceDate(badge.getIssuanceDate());
        badgeResponse.setExpirationDate(badge.getExpirationDate());
        badgeResponse.setActive(badge.getActive());
        return badgeResponse;
    }


}