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

    @PostMapping(value = "/grade", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseBody> createGrade(@RequestParam @Valid CreateGradeRequest request,
                                                    @RequestParam("file") MultipartFile image){
        GeminiAnswerResponse imageResponse = geminiService.multimodal(image);
        if(imageResponse == null){
            throw new AiMultimodalException("não foi possivel obter a resposta da imagem");
        }
        studentService.createGrade(request, imageResponse);
        return ResponseEntity.ok().build();
    }

}
