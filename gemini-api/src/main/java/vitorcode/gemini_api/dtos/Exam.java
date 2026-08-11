package vitorcode.gemini_api.dtos;

import java.util.List;

public record Exam(
        Integer total_questions_detected,
        List<Question> questions
) {}
