package nastya.ru.badge.keeper.service;

import nastya.ru.badge.keeper.api.common.Position;
import nastya.ru.badge.keeper.api.message.AllBadgeMessages;
import nastya.ru.badge.keeper.api.message.BadgeMessage;
import nastya.ru.badge.keeper.api.request.badge.CreateBadgeRequest;
import nastya.ru.badge.keeper.api.response.badge.GetAllBadgesResponse;
import nastya.ru.badge.keeper.api.response.badge.GetBadgeResponse;
import nastya.ru.badge.keeper.entity.Badge;
import nastya.ru.badge.keeper.entity.Employee;
import nastya.ru.badge.keeper.repository.BadgeRepository;
import nastya.ru.badge.keeper.repository.EmployeeRepository;
import nastya.ru.badge.keeper.util.convertion.BadgeConvert;
import nastya.ru.badge.keeper.util.exception.BusinessLogicException;
import nastya.ru.badge.keeper.util.exception.IdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static nastya.ru.badge.keeper.util.convertion.BadgeConvert.toBadge;
import static nastya.ru.badge.keeper.util.convertion.BadgeConvert.toBadgeResponse;

@Service
@Transactional(readOnly = true)
public class BadgeService {
    private BadgeRepository badgeRepository;
    private EmployeeRepository employeeRepository;
    Logger logger = Logger.getLogger(BadgeService.class.getName());
    @Autowired
    public BadgeService(BadgeRepository badgeRepository, EmployeeRepository employeeRepository) {
        this.badgeRepository = badgeRepository;
        this.employeeRepository = employeeRepository;
    }

    public GetBadgeResponse findById(UUID id) {
        logger.info("get badge by id: start: id = %s".formatted(id));

        return toBadgeResponse(
                badgeRepository.findById(id).orElseThrow(() -> new IdNotFoundException("badge", id))
        );
    }

    public GetAllBadgesResponse findAll() {
        logger.info("get all badges: start");

        List<GetBadgeResponse> allBadge = badgeRepository.findAll().stream()
                .map(BadgeConvert::toBadgeResponse)
                .toList();
        return new GetAllBadgesResponse(allBadge);
    }


    public AllBadgeMessages findAllBadgeMessageWhereActiveIsTrue() {
        logger.info("get all badge messages where active is true: start");
        List<BadgeMessage> badgeMessages = badgeRepository.findBadgeByActiveIsTrue().stream()
                .map(BadgeConvert::toBadgeMessage)
                .toList();
        return new AllBadgeMessages(badgeMessages);
    }


    @Transactional
    public void save(CreateBadgeRequest badgeRequest) {
        logger.info("save badge: start: request = %s".formatted(badgeRequest));

        employeeRepository.findById(badgeRequest.getRecipientEmployeeId());
        Employee issueEmployee = employeeRepository.findById(badgeRequest.getIssuerEmployeeId()).orElseThrow();

        if (issueEmployee.getPosition() == Position.SECURITY_OFFICER) {
            Badge badge = toBadge(badgeRequest);
            badge.setId(UUID.randomUUID());
            badge.setIssuanceDate(Instant.now());
            badge.setExpirationDate(Instant.now().plus(365, ChronoUnit.DAYS));
            badge.setActive(true);

            badgeRepository.save(badge);
        } else {
            throw new BusinessLogicException("Сотрудник с данным id = %s не может выдавать бейджики"
                    .formatted(issueEmployee.getId()));
        }
    }

    @Transactional
    public void disable(UUID id) {
        logger.info("disable badge: start: id = %s".formatted(id));

        Badge badge = badgeRepository.findById(id).orElseThrow();
        badge.setActive(false);
    }



}