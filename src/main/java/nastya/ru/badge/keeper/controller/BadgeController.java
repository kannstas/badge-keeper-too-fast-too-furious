package nastya.ru.badge.keeper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nastya.ru.badge.keeper.api.request.badge.CreateBadgeRequest;
import nastya.ru.badge.keeper.service.BadgeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@Tag(name = "Badge controller")
@RestController
@RequestMapping("/badges")
public class BadgeController {

    private BadgeService badgeService;

    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @Operation(
            summary = "Находит бейджик/бейджики.",
            description = "В случае, если в запросе приходит id, вызывается метод поиска бейджика по id, " +
                          "если не приходит - ищутся все бейджики."
    )
    @GetMapping()
    public Object find(@RequestParam(required = false) UUID id){
        if (id!= null) {
            return badgeService.findById(id);
        } else {
            return badgeService.findAll();
        }
    }

    @Operation(
            summary = "Сохраняет бейджик.",
            description = "В сервисе происходит проверка возможности выдачи бейджика."
    )
    @PostMapping()
    public ResponseEntity<HttpStatus> save(@RequestBody CreateBadgeRequest badgeRequest) {
        badgeService.save(badgeRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Деактивирует бейджик.",
            description = "В моём приложении нет удаления бейджиков, есть их деактивация."
    )
    @PatchMapping()
    public ResponseEntity<HttpStatus> disable(@RequestParam UUID id) {
        badgeService.disable(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}