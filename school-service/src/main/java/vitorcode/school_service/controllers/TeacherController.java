package vitorcode.school_service.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitorcode.school_service.config.TeacherFilter;
import vitorcode.school_service.dtos.*;
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

    @GetMapping("/read")
    public PageResponse<TeacherResponse> read(
            TeacherFilter filter,
            Pageable pageable
    ) {
        return adminTeacherService.read(filter, pageable);
    }

    @PostMapping("/update/subject/add")
    public ResponseEntity<ResponseBody> update(@RequestBody @Valid UpdateTeacherSubjectRequest request){
        adminTeacherService.update(request);

        return ResponseEntity.ok().build();
    }
}
