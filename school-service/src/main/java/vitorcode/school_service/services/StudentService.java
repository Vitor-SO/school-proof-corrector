package vitorcode.school_service.services;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vitorcode.school_service.config.StudentFilterSpecification;
import vitorcode.school_service.database.entities.*;
import vitorcode.school_service.dtos.*;
import vitorcode.school_service.exceptions.CalculateGradeException;
import vitorcode.school_service.exceptions.CustomNotFoundException;
import vitorcode.school_service.repositories.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;
    private final SubjectRepository subjectRepository;
    private final PeriodRepository periodRepository;
    private final BulletinRepository bulletinRepository;
    private final AcademicLevelRepository academicLevelRepository;
    private final AnswersKeyRepository answersKeyRepository;

    public StudentService(StudentRepository studentRepository, ClassRepository classRepository,
                          SubjectRepository subjectRepository, PeriodRepository periodRepository,
                          BulletinRepository bulletinRepository, AcademicLevelRepository academicLevelRepository,
                          AnswersKeyRepository answersKeyRepository) {
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
        this.subjectRepository = subjectRepository;
        this.periodRepository = periodRepository;
        this.bulletinRepository = bulletinRepository;
        this.academicLevelRepository = academicLevelRepository;
        this.answersKeyRepository = answersKeyRepository;
    }

    public void create(@Valid CreateStudentRequest dto) {
        AcademicLevelEntity academicLevelEntity = academicLevelRepository.findById(dto.academicLevelId())
                .orElseThrow(() -> new CustomNotFoundException("Nivel Academico não encontrado"));

            StudentsEntity entity = new StudentsEntity();
            entity.setName(dto.name());
            entity.setAcademicLevel(academicLevelEntity);
            studentRepository.save(entity);
    }

    public void createGrade(@Valid CreateGradeRequest dto, GeminiAnswerResponse frontResponse, GeminiAnswerResponse backResponse) {

        StudentsEntity studentsEntity = studentRepository.findById(dto.studentId())
                .orElseThrow(() -> new CustomNotFoundException("Estudante não encontrado"));

        SubjectEntity subjectEntity = subjectRepository.findById(dto.subjectId())
                .orElseThrow(() -> new CustomNotFoundException("Disciplina não encontrada"));

        PeriodEntity periodEntity = periodRepository.findById(dto.periodId())
                .orElseThrow(() -> new CustomNotFoundException("Período não encontrado"));

        List<AnswersKeyEntity> answersKeyList = answersKeyRepository.findBySubjectId(subjectEntity.getId());

        if (answersKeyList.isEmpty()) {
            throw new CustomNotFoundException("Nenhuma resposta-chave encontrada para a disciplina");
        }

        Map<Integer, String> frontAnswerMap = frontResponse.answers().stream()
                .filter(a -> a.answer() != null)
                .collect(Collectors.toMap(
                        Answer::question,
                        Answer::answer
                ));

        Map<Integer, String> backAnswerMap = backResponse.answers().stream()
                .filter(a -> a.answer() != null)
                .collect(Collectors.toMap(
                        Answer::question,
                        Answer::answer
                ));

        Map<Integer, String> answersMap = new HashMap<>();
        answersMap.putAll(frontAnswerMap);
        answersMap.putAll(backAnswerMap);

        BigDecimal grade = answersKeyList.stream()
                .filter(key -> Objects.equals(
                        key.getAnswer(),
                        answersMap.get(key.getQuestion())
                ))
                .map(AnswersKeyEntity::getGradePerQuestion)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if(grade == null){
            throw new CalculateGradeException("Não foi possível calcular a nota do aluno");
        }

        BulletinEntity bulletinEntity = new BulletinEntity();
        bulletinEntity.setStudent(studentsEntity);
        bulletinEntity.setSubject(subjectEntity);
        bulletinEntity.setAcademicLevel(studentsEntity.getAcademicLevel());
        bulletinEntity.setPeriod(periodEntity);
        bulletinEntity.setGrade(grade);
        bulletinRepository.save(bulletinEntity);
        studentsEntity.setBulletin(bulletinEntity);
        studentRepository.save(studentsEntity);
    }

    public PageResponse<StudentResponse> read(StudentFilter filter, Pageable pageable) {
        Page<StudentsEntity> page = studentRepository.findAll(StudentFilterSpecification.filter(filter), pageable);

        return new PageResponse<>(
                page.getContent().stream()
                        .map(StudentResponse::fromEntity)
                        .toList(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize()
        );
    }
}
