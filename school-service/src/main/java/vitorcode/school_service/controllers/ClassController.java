package vitorcode.school_service.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitorcode.school_service.dtos.CreateClassRequest;
import vitorcode.school_service.services.ClassService;

@RestController
@RequestMapping("/class")
public class ClassController {
    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseBody> create(@RequestBody @Valid CreateClassRequest request){
        classService.create(request);
        return ResponseEntity.ok().build();
    }
}
