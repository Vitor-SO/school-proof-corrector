package vitorcode.school_service.services;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vitorcode.school_service.config.SubjectFilterSpecification;
import vitorcode.school_service.database.entities.AnswersKeyEntity;
import vitorcode.school_service.database.entities.SubjectEntity;
import vitorcode.school_service.dtos.*;
import vitorcode.school_service.exceptions.AlreadyExistsException;
import vitorcode.school_service.exceptions.CustomNotFoundException;
import vitorcode.school_service.repositories.AnswersKeyRepository;
import vitorcode.school_service.repositories.SubjectRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final AnswersKeyRepository answersKeyRepository;

    public SubjectService(SubjectRepository subjectRepository, AnswersKeyRepository answersKeyRepository) {
        this.subjectRepository = subjectRepository;
        this.answersKeyRepository = answersKeyRepository;
    }

    public void create(@Valid CreateSubjectRequest dto) {
        boolean subjectNameAlreadyExists = subjectRepository.findByName(dto.name()).isPresent();

        if (subjectNameAlreadyExists) {
            throw new AlreadyExistsException("O nome da disciplina ja existe");
        }

        SubjectEntity entity = new SubjectEntity();
        entity.setName(dto.name());
        subjectRepository.save(entity);
    }

    public void createAnswersKey(@Valid CreateAnswersKeyRequest dto) {
        SubjectEntity subjectEntity = subjectRepository.findById(dto.subjectId())
                .orElseThrow(() -> new CustomNotFoundException("o id da disciplina nao foi encontrado"));

        for (Answer answer : dto.answers()) {
            AnswersKeyEntity entity = new AnswersKeyEntity();
            entity.setSubject(subjectEntity);
            entity.setGradePerQuestion(dto.gradePerQuestion());
            entity.setQuestion(answer.question());
            entity.setAnswer(answer.answer());
            answersKeyRepository.save(entity);
        }
    }

    public PageResponse<SubjectResponse> read(SubjectFilter filter, Pageable pageable) {
        Page<SubjectEntity> page  = subjectRepository.findAll(SubjectFilterSpecification.filter(filter), pageable);

        return new PageResponse<>(
                page.getContent().stream().map(SubjectResponse::fromEntity).toList(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize()
        );
    }

    public void update(UUID id, @Valid UpdateSubjectRequest dto) {
        SubjectEntity subjectEntity = subjectRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Disciplina nao foi encontrada"));

        subjectEntity.setName(dto.name());
        subjectRepository.save(subjectEntity);
    }

    public void delete(UUID id) {
        subjectRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Disciplina nao foi encontrada"));
        subjectRepository.deleteById(id);
    }

    public void updateAnswersKey(UUID id, @Valid UpdateAnswersKeyRequest dto) {
        AnswersKeyEntity answersKeyEntity = answersKeyRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Cartão de respostas nao foi encontrado"));

        if(dto.gradePerQuestion() == null || dto.answers().isEmpty()){
            throw new CustomNotFoundException("Respostas e nota por questão são obrigatórias");
        }

        answersKeyEntity.setGradePerQuestion(dto.gradePerQuestion());
        List<AnswersKeyEntity> currentAnswers = answersKeyEntity.getSubject().getAnswersKey().stream().toList();
        List<AnswersKeyEntity> newAnswers = dto.answers();

        Set<UUID> newIds = newAnswers.stream().map()

    }
}
