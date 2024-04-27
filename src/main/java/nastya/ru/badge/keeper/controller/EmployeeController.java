package nastya.ru.badge.keeper.controller;

import nastya.ru.badge.keeper.api.request.employee.CreateEmployeeRequest;
import nastya.ru.badge.keeper.api.request.employee.UpdateEmployeeRequest;
import nastya.ru.badge.keeper.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public Object find(@RequestParam(required = false) UUID id) {
        if (id != null) {
            return employeeService.findById(id);
        } else {
            return employeeService.findAll();
        }
    }

    @PostMapping
    public ResponseEntity<HttpStatus> save(@RequestBody CreateEmployeeRequest employeeRequest) {
        employeeService.save(employeeRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<HttpStatus> update(@RequestBody UpdateEmployeeRequest employeeRequest) {
        employeeService.update(employeeRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<HttpStatus> delete(@RequestParam UUID id) {
        employeeService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}