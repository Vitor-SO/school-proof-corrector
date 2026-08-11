package vitorcode.gemini_api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vitorcode.gemini_api.dtos.GeminiAnswerResponse;
import vitorcode.gemini_api.services.GeminiService;

@RestController
@RequestMapping("/gemini")
public class GeminiController {
    private static final Logger log = LoggerFactory.getLogger(GeminiController.class);

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping(value = "/process", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseBody> process(
            @RequestParam(value = "front", required = false) MultipartFile front,
            @RequestParam(value = "back", required = false) MultipartFile back)
    {
        log.info(front.toString());
        GeminiAnswerResponse frontResponse = geminiService.multimodal(front);
        log.info("Front response: {}", frontResponse);
//        GeminiAnswerResponse backResponse = geminiService.multimodal(back);

        return ResponseEntity.ok().build();
    }
}
