package vitorcode.school_service.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitorcode.school_service.dtos.CreateSubjectRequest;
import vitorcode.school_service.services.SubjectService;

@RestController
@RequestMapping("/subject")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseBody> create(@RequestBody @Valid CreateSubjectRequest request){
        subjectService.create(request);

        return ResponseEntity.ok().build();
    }
}
