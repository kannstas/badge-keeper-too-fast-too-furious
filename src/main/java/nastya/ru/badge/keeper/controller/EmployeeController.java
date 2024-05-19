package nastya.ru.badge.keeper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nastya.ru.badge.keeper.api.request.employee.CreateEmployeeRequest;
import nastya.ru.badge.keeper.api.request.employee.UpdateEmployeeRequest;
import nastya.ru.badge.keeper.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@Tag(name = "Employee controller")
@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(
            summary = "Находит работника/работников.",
            description = "В случае, если в запросе приходит id, вызывается метод поиска работника по id, " +
                          "если не приходит - ищутся все работники."
    )
    @GetMapping()
    public Object find(@RequestParam(required = false) UUID id) {
        if (id != null) {
            return employeeService.findById(id);
        } else {
            return employeeService.findAll();
        }
    }
    @Operation(
            summary = "Добавляет работника."
    )
    @PostMapping()
    public ResponseEntity<HttpStatus> save(@RequestBody CreateEmployeeRequest employeeRequest) {
        employeeService.save(employeeRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Изменяет данные о работнике.",
            description = "Если какой-то параметр не будет передан в реквесте, в базе данных он останется прежним."
    )
    @PatchMapping()
    public ResponseEntity<HttpStatus> update(@RequestBody UpdateEmployeeRequest employeeRequest) {
        employeeService.update(employeeRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Удаляет работника по id.",
            description = "При удалении работника из бд, каскадно удаляются его персональные данные."
    )
    @DeleteMapping()
    public ResponseEntity<HttpStatus> delete(@RequestParam UUID id) {
        employeeService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}