package vitorcode.school_service.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitorcode.school_service.dtos.*;
import vitorcode.school_service.services.SubjectService;

import java.util.UUID;

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


    @GetMapping("/read")
    public PageResponse<SubjectResponse> read(SubjectFilter filter, Pageable pageable){
        return subjectService.read(filter, pageable);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody @Valid UpdateSubjectRequest request){
        subjectService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        subjectService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("answers-key")
    public ResponseEntity<Void> createAnswersKey(@RequestBody @Valid CreateAnswersKeyRequest request){
        subjectService.createAnswersKey(request);

        return ResponseEntity.ok().build();
    }

    @PutMapping("answers-key/update/{id}")
    public ResponseEntity<Void> updateAnswersKey(@PathVariable UUID id, @RequestBody @Valid UpdateAnswersKeyRequest request){
        subjectService.updateAnswersKey(id, request);

        return ResponseEntity.ok().build();
    }
}
