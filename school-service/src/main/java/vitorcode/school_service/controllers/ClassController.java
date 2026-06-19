package vitorcode.school_service.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitorcode.school_service.config.ClassFilter;
import vitorcode.school_service.dtos.CreateClassRequest;
import vitorcode.school_service.dtos.PageResponse;
import vitorcode.school_service.dtos.ReadClassResponse;
import vitorcode.school_service.dtos.UpdateClassRequest;
import vitorcode.school_service.services.ClassService;

import java.util.UUID;

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

    @GetMapping("/read")
    public PageResponse<ReadClassResponse> read(
            ClassFilter filter,
            Pageable pageable
    ) {
        return classService.read(filter, pageable);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody UpdateClassRequest request){
        classService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        classService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
