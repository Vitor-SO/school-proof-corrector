package vitorcode.school_service.controllers;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vitorcode.school_service.dtos.*;
import vitorcode.school_service.services.GeminiService;
import vitorcode.school_service.services.StudentService;

import java.util.UUID;

@RestController
@RequestMapping("/student")
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;
    private final GeminiService geminiService;

    public StudentController(StudentService studentService, GeminiService geminiService) {
        this.studentService = studentService;
        this.geminiService = geminiService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseBody> create(@RequestBody @Valid CreateStudentRequest request){
        studentService.create(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/bulletin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseBody> createGrade(
            @RequestParam("studentId") UUID studentId,
            @RequestParam("subjectId") UUID subjectId,
            @RequestParam("periodId") UUID periodId,
            @RequestParam(value = "front", required = false) MultipartFile front,
            @RequestParam(value = "back", required = false) MultipartFile back)
        {
            GeminiAnswerResponse frontResponse = geminiService.multimodal(front);
            GeminiAnswerResponse backResponse = geminiService.multimodal(back);

            CreateGradeRequest gradeRequest = new CreateGradeRequest(
                    studentId,
                    subjectId,
                    periodId
            );
            studentService.createGrade(gradeRequest, frontResponse, backResponse);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/read")
    public PageResponse<StudentResponse> read(
            StudentFilter filter,
            Pageable pageable
    ){
        return studentService.read(filter, pageable);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody UpdateStudentRequest request){
        studentService.update(id, request);
        return ResponseEntity.ok().build();
    }
}
