package vitorcode.school_service.services;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
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
            throw new CustomNotFoundException("Nenhum item de resposta associado a disciplina foi encontrado");
        }

        Map<Integer, String> frontAnswerMap =
                Optional.ofNullable(frontResponse.answers())
                        .orElse(Collections.emptyList())
                        .stream()
                        .filter(Objects::nonNull)
                        .filter(a -> a.question() != null)
                        .filter(a -> a.answer() != null)
                        .collect(Collectors.toMap(
                                Answer::question,
                                Answer::answer,
                                (oldValue, newValue) -> newValue
                        ));
	log.info("frontAnswerMap {}", frontAnswerMap);
        Map<Integer, String> backAnswerMap =
                Optional.ofNullable(backResponse.answers())
                        .orElse(Collections.emptyList())
                        .stream()
                        .filter(Objects::nonNull)
                        .filter(a -> a.question() != null)
                        .filter(a -> a.answer() != null)
                        .collect(Collectors.toMap(
                                Answer::question,
                                Answer::answer,
                                (oldValue, newValue) -> newValue
                        ));
	log.info("backAnswerMap {}", backAnswerMap);

        Map<Integer, String> answersMap = new HashMap<>();
        answersMap.putAll(frontAnswerMap);
        answersMap.putAll(backAnswerMap);

	log.info("answersMap {}", answersMap);

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

	log.info("grade {}", grade);

        BulletinEntity bulletinEntity = new BulletinEntity();
        bulletinEntity.setStudent(studentsEntity);
        bulletinEntity.setSubject(subjectEntity);
        bulletinEntity.setAcademicLevel(studentsEntity.getAcademicLevel());
        bulletinEntity.setPeriod(periodEntity);
        bulletinEntity.setGrade(grade);
        bulletinRepository.save(bulletinEntity);
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

    public void update(UUID id, UpdateStudentRequest dto) {
        StudentsEntity student = studentRepository.findById(id).orElseThrow(() -> new CustomNotFoundException("Estudante não encontrado"));
        student.setName(dto.name());
        if(dto.classId() != null){
            ClassEntity classEntity = classRepository.findById(dto.classId()).orElseThrow(() -> new CustomNotFoundException("Turma não encontrada"));
            student.setClassEntity(classEntity);
        }
        AcademicLevelEntity academicLevelEntity = academicLevelRepository.findById(dto.academicLevelId())
                .orElseThrow(() -> new CustomNotFoundException("Nivel Academico não foi encontrado"));
        student.setAcademicLevel(academicLevelEntity);
        studentRepository.save(student);
    }
}
