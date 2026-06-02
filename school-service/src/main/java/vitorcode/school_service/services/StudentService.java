package vitorcode.school_service.services;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import vitorcode.school_service.database.entities.*;
import vitorcode.school_service.dtos.Answer;
import vitorcode.school_service.dtos.CreateGradeRequest;
import vitorcode.school_service.dtos.CreateStudentRequest;
import vitorcode.school_service.dtos.GeminiAnswerResponse;
import vitorcode.school_service.exceptions.CalculateGradeException;
import vitorcode.school_service.exceptions.CustomNotFoundException;
import vitorcode.school_service.repositories.*;

import java.math.BigDecimal;
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
    private final SchoolGradesRepository schoolGradesRepository;
    private final SchoolYearRepository schoolYearRepository;
    private final AnswersKeyRepository answersKeyRepository;

    public StudentService(StudentRepository studentRepository, ClassRepository classRepository, SubjectRepository subjectRepository, PeriodRepository periodRepository, SchoolGradesRepository schoolGradesRepository, SchoolYearRepository schoolYearRepository, AnswersKeyRepository answersKeyRepository) {
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
        this.subjectRepository = subjectRepository;
        this.periodRepository = periodRepository;
        this.schoolGradesRepository = schoolGradesRepository;
        this.schoolYearRepository = schoolYearRepository;
        this.answersKeyRepository = answersKeyRepository;
    }

    public void create(@Valid CreateStudentRequest dto) {
        SchoolYearEntity schoolYearEntity = schoolYearRepository.findById(dto.schoolYearId())
                .orElseThrow(() -> new CustomNotFoundException("Ano letivo não encontrado"));

            StudentEntity entity = new StudentEntity();
            entity.setName(dto.name());
            entity.setSchoolYear(schoolYearEntity);
            studentRepository.save(entity);
    }

    public void createGrade(@Valid CreateGradeRequest dto, GeminiAnswerResponse imageResponse) {

        ClassEntity classEntity = classRepository.findById(dto.classId())
                .orElseThrow(() -> new CustomNotFoundException("Turma não encontrada"));

        SubjectEntity subjectEntity = subjectRepository.findById(dto.subjectId())
                .orElseThrow(() -> new CustomNotFoundException("Disciplina não encontrada"));

        PeriodEntity periodEntity = periodRepository.findById(dto.periodId())
                .orElseThrow(() -> new CustomNotFoundException("Período não encontrado"));

        StudentEntity studentEntity = studentRepository.findById(dto.studentId())
                .orElseThrow(() -> new CustomNotFoundException("Estudante não encontrado"));

        List<AnswersKeyEntity> answersKeyList = answersKeyRepository.findBySubjectId(subjectEntity.getId());

        if (answersKeyList.isEmpty()) {
            throw new CustomNotFoundException("Nenhuma resposta-chave encontrada para a disciplina");
        }

        Map<Integer, String> studentAnswerMap = imageResponse.answers().stream()
                .collect(Collectors.toMap(
                        Answer::question,
                        Answer::answer
                ));

        BigDecimal grade = answersKeyList.stream()
                .filter(key -> Objects.equals(
                        key.getAnswer(),
                        studentAnswerMap.get(key.getQuestion())
                ))
                .map(AnswersKeyEntity::getGradePerQuestion)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if(grade == null){
            throw new CalculateGradeException("Não foi possível calcular a nota do aluno");
        }

        SchoolGradesEntity schoolGradesEntity = new SchoolGradesEntity();
        schoolGradesEntity.setGrade(grade);
        schoolGradesEntity.setClassEntity(classEntity);
        schoolGradesEntity.setSchoolYear(studentEntity.getSchoolYear());
        schoolGradesEntity.setSubject(subjectEntity);
        schoolGradesEntity.setPeriod(periodEntity);
        schoolGradesRepository.save(schoolGradesEntity);
        studentEntity.setSchoolGrades(schoolGradesEntity);
        studentRepository.save(studentEntity);
    }
}
