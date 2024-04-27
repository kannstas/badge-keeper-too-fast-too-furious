package nastya.ru.badge.keeper.controller;

import nastya.ru.badge.keeper.api.request.employee.personal.data.CreateEmployeePersonalDataRequest;
import nastya.ru.badge.keeper.api.request.employee.personal.data.UpdateEmployeePersonalDataRequest;
import nastya.ru.badge.keeper.service.EmployeePersonalDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/data")
public class EmployeePersonalDataController {
    private EmployeePersonalDataService personalDataService;

    public EmployeePersonalDataController(EmployeePersonalDataService personalDataService) {
        this.personalDataService = personalDataService;
    }

    @GetMapping()
    public Object find(@RequestParam(required = false) UUID employeeId) {
        if(employeeId!= null) {
            return personalDataService.findByEmployeeId(employeeId);
        }else {
            return personalDataService.findAll();
        }
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> save(@RequestBody CreateEmployeePersonalDataRequest request) {
        personalDataService.save(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<HttpStatus> update(@RequestBody UpdateEmployeePersonalDataRequest request){
        personalDataService.update(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> delete(@RequestParam UUID id) {
        personalDataService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}