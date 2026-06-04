package vitorcode.school_service.controllers;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vitorcode.school_service.dtos.CreateGradeRequest;
import vitorcode.school_service.dtos.CreateStudentRequest;
import vitorcode.school_service.dtos.GeminiAnswerResponse;
import vitorcode.school_service.exceptions.AiMultimodalException;
import vitorcode.school_service.services.GeminiService;
import vitorcode.school_service.services.StudentService;

import java.util.UUID;

@RestController
@RequestMapping("/student")
public class StudentController {
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
            @RequestParam("front") MultipartFile front,
            @RequestParam("back") MultipartFile back)
        {
            GeminiAnswerResponse frontResponse = geminiService.multimodal(front);
            if (frontResponse == null) {
                throw new AiMultimodalException("Não foi possivel obter a resposta da imagem frontal");
            }

            GeminiAnswerResponse backResponse = geminiService.multimodal(back);
            if (backResponse == null) {
                throw new AiMultimodalException("Não foi possivel obter a resposta da imagem do verso");
            }

            CreateGradeRequest gradeRequest = new CreateGradeRequest(
                    studentId,
                    subjectId,
                    periodId
            );
        studentService.createGrade(gradeRequest, frontResponse, backResponse);
            return ResponseEntity.ok().build();
    }

}
