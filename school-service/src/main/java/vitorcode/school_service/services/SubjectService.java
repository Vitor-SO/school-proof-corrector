package vitorcode.school_service.services;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import vitorcode.school_service.database.entities.AnswersKeyEntity;
import vitorcode.school_service.database.entities.SubjectEntity;
import vitorcode.school_service.dtos.Answer;
import vitorcode.school_service.dtos.CreateAnswersKeyRequest;
import vitorcode.school_service.dtos.CreateSubjectRequest;
import vitorcode.school_service.exceptions.AlreadyExistsException;
import vitorcode.school_service.exceptions.CustomNotFoundException;
import vitorcode.school_service.repositories.AnswersKeyRepository;
import vitorcode.school_service.repositories.ClassRepository;
import vitorcode.school_service.repositories.SubjectRepository;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final ClassRepository classRepository;
    private final AnswersKeyRepository answersKeyRepository;

    public SubjectService(SubjectRepository subjectRepository, ClassRepository classRepository, AnswersKeyRepository answersKeyRepository) {
        this.subjectRepository = subjectRepository;
        this.classRepository = classRepository;
        this.answersKeyRepository = answersKeyRepository;
    }

    public void create(@Valid CreateSubjectRequest dto) {
        boolean classIdExists = classRepository.findById(dto.classId()).isPresent();
        if (!classIdExists) {
            throw new CustomNotFoundException("o id da turma nao foi encontrado");
        }

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
}
