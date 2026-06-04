package vitorcode.school_service.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitorcode.school_service.dtos.CreateTeacherRequest;
import vitorcode.school_service.services.TeacherService;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherService adminTeacherService;

    public TeacherController(TeacherService adminTeacherService) {
        this.adminTeacherService = adminTeacherService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseBody> create(@RequestBody @Valid CreateTeacherRequest request){
        adminTeacherService.create(request);

        return ResponseEntity.ok().build();
    }
}
