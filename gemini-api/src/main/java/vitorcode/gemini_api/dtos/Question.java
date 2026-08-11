package vitorcode.gemini_api.dtos;

import java.util.List;

public record Question(
        Integer number,
        List<String> available_alternatives,
        String marked_answer,
        String confidence,
        String note
) {
}
