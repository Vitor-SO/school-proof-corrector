package vitorcode.school_service.services;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import vitorcode.school_service.dtos.GeminiAnswerResponse;
import vitorcode.school_service.exceptions.AiMultimodalException;

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
            return new GeminiAnswerResponse(List.of());
        }
        BeanOutputConverter<GeminiAnswerResponse> converter =
                new BeanOutputConverter<>(GeminiAnswerResponse.class);

        String format = converter.getFormat();
        try{
            var userMessage = UserMessage.builder()
                    .text("""
                            Leia a prova.
                            
                            Identifique a alternativa marcada com X em cada questão.
                            
                            Use null quando não houver marcação clara.
                            %s
                            """.formatted(format))
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
