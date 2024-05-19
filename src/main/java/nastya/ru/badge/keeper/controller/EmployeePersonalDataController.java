package nastya.ru.badge.keeper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nastya.ru.badge.keeper.api.request.employee.personal.data.CreateEmployeePersonalDataRequest;
import nastya.ru.badge.keeper.api.request.employee.personal.data.UpdateEmployeePersonalDataRequest;
import nastya.ru.badge.keeper.service.EmployeePersonalDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@Tag(name = "Employee personal data controller")
@RestController
@RequestMapping("/data")
public class EmployeePersonalDataController {
    private EmployeePersonalDataService personalDataService;

    public EmployeePersonalDataController(EmployeePersonalDataService personalDataService) {
        this.personalDataService = personalDataService;
    }

    @Operation(
            summary = "Находит персональные данные работника/работников по id работника.",
            description = "В случае, если в запросе приходит id, вызывается метод поиска персональных данных работника по id, " +
                          "если не приходит - ищутся персональные данные всех работников."
    )
    @GetMapping()
    public Object find(@RequestParam(required = false) UUID employeeId) {
        if(employeeId!= null) {
            return personalDataService.findByEmployeeId(employeeId);
        }else {
            return personalDataService.findAll();
        }
    }
    @Operation(
            summary = "Сохранение персональных данных работника."
    )
    @PostMapping()
    public ResponseEntity<HttpStatus> save(@RequestBody CreateEmployeePersonalDataRequest request) {
        personalDataService.save(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Изменяет персональные данные работника.",
            description = "Если какой-то параметр не будет передан в реквесте, в базе данных он останется прежним."
    )
    @PatchMapping()
    public ResponseEntity<HttpStatus> update(@RequestBody UpdateEmployeePersonalDataRequest request){
        personalDataService.update(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Удаляет персональные данные работника по id.",
            description = "При удалении персональных данных из бд, запись о работнике удалена не будет."
    )
    @DeleteMapping()
    public ResponseEntity<HttpStatus> delete(@RequestParam UUID id) {
        personalDataService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}