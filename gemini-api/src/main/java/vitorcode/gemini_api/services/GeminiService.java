package vitorcode.gemini_api.services;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import vitorcode.gemini_api.dtos.Exam;
import vitorcode.gemini_api.dtos.GeminiAnswerResponse;
import vitorcode.gemini_api.exceptions.AiMultimodalException;

import java.util.List;
import java.util.Objects;

@Service
public class GeminiService {
    private final ChatModel chatModel;

    public GeminiService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public GeminiAnswerResponse multimodal(MultipartFile image) {
        if(image == null){
            return new GeminiAnswerResponse(new Exam(0, List.of()));
        }
        BeanOutputConverter<GeminiAnswerResponse> converter =
                new BeanOutputConverter<>(GeminiAnswerResponse.class);

        try{
            var userMessage = UserMessage.builder()
                    .text("""
                            You are a multimodal agent specialized in analyzing exam/answer sheet images.
                            
                            ## LANGUAGE NOTE
                             The image provided is written in Brazilian Portuguese (pt-BR). All question text, instructions, and labels in the image will be in Brazilian Portuguese. Read and interpret the content accordingly, but return all output in the JSON structure specified below using English field names as instructed.
                            
                            ## TASK
                             Analyze the provided image of an exam/answer sheet and identify all questions that have an alternative marked with an "X" (or equivalent marking, such as a filled circle, a scribble over the letter, or pen/pencil covering the option).
                            
                            ## ANALYSIS INSTRUCTIONS
                             1. Go through the image question by question, in the order they appear (top to bottom, left to right).
                             2. For each question, identify:
                                - The question number.
                                - All available alternatives (A, B, C, D, E — or according to the exam's format).
                                - Which alternative is marked with an X (or similar marking).
                             3. If a question has no marking at all, return the "marked_answer" field as null.
                             4. If a question has more than one alternative marked (double/ambiguous marking), return all marked options in a list and add "note": "multiple or ambiguous marking".
                             5. If the marking appears crossed out (an attempt to void the answer), record this in "note": "marking crossed out / voided".
                             6. Do not guess or invent answers. If it's not possible to identify a marking with confidence, set "marked_answer": "undetermined" and explain in "note".
                             7. Ignore headers, student information, instructions, and footers — extract only the questions and their markings.
                            
                            ## OUTPUT FORMAT
                             Return **exclusively** a valid JSON, with no additional text before or after, following this structure:
                            
                             {
                               "exam": {
                                 "total_questions_detected": 0,
                                 "questions": [
                                   {
                                     "number": 1,
                                     "available_alternatives": ["A", "B", "C", "D", "E"],
                                     "marked_answer": "C",
                                     "confidence": "high",
                                     "note": null
                                   }
                                 ]
                               }
                             }
                            
                            ## ADDITIONAL RULES
                             - The "confidence" field must be "high", "medium", or "low", based on how clear the marking is in the image.
                             - Never omit a question, even if it has no marking — include it with "marked_answer": null.
                             - Keep the numbering exactly as it appears in the exam (do not reorder or skip numbers).
                             - Do not add comments, markdown, or explanations outside the JSON.
                             - If the image is illegible or not an exam, return: {"error": "illegible image or out of expected scope"}
                            """)
                    .media(List.of(new Media(
                            MimeTypeUtils.IMAGE_JPEG,
                            image.getResource())
                    )).build();

            ChatResponse response = chatModel.call(new Prompt(List.of(userMessage)));
            return converter.convert(Objects.requireNonNull(response.getResult().getOutput().getText()));
        }catch (Exception e){
            throw new AiMultimodalException(e.getMessage());
        }
    }
}
