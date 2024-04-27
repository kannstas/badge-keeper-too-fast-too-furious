package nastya.ru.badge.keeper.controller;

import nastya.ru.badge.keeper.api.request.badge.CreateBadgeRequest;
import nastya.ru.badge.keeper.service.BadgeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/badges")
public class BadgeController {

    private BadgeService badgeService;

    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @GetMapping
    public Object find(@RequestParam(required = false) UUID id){
        if (id!= null) {
            return badgeService.findById(id);
        } else {
            return badgeService.findAll();
        }
    }

    @PostMapping
    public ResponseEntity<HttpStatus> save(@RequestBody CreateBadgeRequest badgeRequest) {
        badgeService.save(badgeRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<HttpStatus> disable(@RequestParam UUID id) {
        badgeService.disable(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}