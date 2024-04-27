package nastya.ru.badge.keeper.service;

import nastya.ru.badge.keeper.api.common.Position;
import nastya.ru.badge.keeper.api.request.badge.CreateBadgeRequest;
import nastya.ru.badge.keeper.api.response.badge.GetBadgeResponse;
import nastya.ru.badge.keeper.entity.Badge;
import nastya.ru.badge.keeper.entity.Employee;
import nastya.ru.badge.keeper.repository.BadgeRepository;
import nastya.ru.badge.keeper.repository.EmployeeRepository;
import nastya.ru.badge.keeper.util.exception.BusinessLogicException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class BadgeService {
    private BadgeRepository badgeRepository;
    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;

    @Autowired
    public BadgeService(BadgeRepository badgeRepository, EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.badgeRepository = badgeRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public GetBadgeResponse findById(UUID id) {
        return toBadgeResponse(
                badgeRepository.findById(id).orElseThrow()
        );
    }

    public List<GetBadgeResponse> findAll() {
        return badgeRepository.findAll().stream()
                .map(this::toBadgeResponse)
                .toList();
    }

    @Transactional
    public void save(CreateBadgeRequest badgeRequest) {
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
        Badge badge = badgeRepository.findById(id).orElseThrow();
        badge.setActive(false);
    }


    private Badge toBadge(CreateBadgeRequest badgeRequest) {
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

    private GetBadgeResponse toBadgeResponse(Badge badge) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        GetBadgeResponse badgeResponse = modelMapper.map(badge, GetBadgeResponse.class);

        badgeResponse.setRecipientEmployeeId(badge.getRecipientEmployee().getId());
        badgeResponse.setIssuerEmployeeId(badge.getIssuerEmployee().getId());

        return badgeResponse;
    }
}