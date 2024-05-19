package nastya.ru.badge.keeper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nastya.ru.badge.keeper.api.message.AllBadgeMessages;
import nastya.ru.badge.keeper.service.BadgeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "Scanner controller")
@RestController
@RequestMapping("/scanner")
public class ScannerController {

    private final BadgeService badgeService;

    public ScannerController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @Operation(
            summary = "Отправляет только активные бейджики",
            description = "Ручка была создана для взаимодействия с приложением badgeKeeperCustomer. По запросу она достает бейджики, у которых поле 'active' = 'true'"
    )
    @GetMapping()
    public AllBadgeMessages sendAllBadgesWhereActiveIsTrue() {
        return badgeService.findAllBadgeMessageWhereActiveIsTrue();
    }
}