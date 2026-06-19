package vitorcode.school_service.services;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vitorcode.school_service.config.TeacherFilter;
import vitorcode.school_service.config.TeacherFilterSpecification;
import vitorcode.school_service.database.entities.*;
import vitorcode.school_service.dtos.*;
import vitorcode.school_service.exceptions.CreateTeacherException;
import vitorcode.school_service.exceptions.CreateTeacherParamsIsNullException;
import vitorcode.school_service.exceptions.CustomNotFoundException;
import vitorcode.school_service.exceptions.UpdateTeacherSubjectException;
import vitorcode.school_service.repositories.*;

@Service
public class TeacherService {
    private final UsersRepository usersRepository;
    private final TeacherRepository teacherRepository;
    private final RoleRepository roleRepository;
    private final SubjectRepository subjectRepository;

    public TeacherService(UsersRepository usersRepository, TeacherRepository teacherRepository, RoleRepository roleRepository, SubjectRepository subjectRepository) {
        this.usersRepository = usersRepository;
        this.teacherRepository = teacherRepository;
        this.roleRepository = roleRepository;
        this.subjectRepository = subjectRepository;
    }

    public void create(CreateTeacherRequest dto){
        if (dto.name().isEmpty() || dto.email().isEmpty() ||
                dto.password().isEmpty()) {
            throw new CreateTeacherParamsIsNullException("Verifique os dados enviados");
        }

        Roles role = roleRepository.findByName("professor");
        if (role == null) {
            throw new CreateTeacherException("Role professor not found");
        }

        SubjectEntity subjectEntity = null;

        if (dto.subjectId() != null) {
            subjectEntity = subjectRepository.findById(dto.subjectId())
                    .orElseThrow(() -> new CustomNotFoundException("Disciplina não encontrada"));
        }

        TeachersEntity hasTeacherCreated = teacherRepository.findByName(dto.name());
        if (hasTeacherCreated != null) {
            throw new CreateTeacherException("Professor já cadastrado");
        }

        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setName(dto.name());
        usersEntity.setEmail(dto.email());
        usersEntity.setPassword(new BCryptPasswordEncoder().encode(dto.password()));
        usersEntity.setRole(role);
        usersRepository.save(usersEntity);
        TeachersEntity teacher = new TeachersEntity();
        teacher.setName(dto.name());
        teacher.setUser(usersEntity);
        if(subjectEntity != null){
            teacher.addSubject(subjectEntity);
        }
        teacherRepository.save(teacher);
    }

    public PageResponse<TeacherResponse> read(TeacherFilter filter, Pageable pageable) {
        Page<TeachersEntity> pageTeachers = teacherRepository.findAll(TeacherFilterSpecification.filter(filter), pageable);

        return new PageResponse<>(
                pageTeachers.getContent().stream()
                        .map(TeacherResponse::fromEntity)
                        .toList(),
                pageTeachers.getTotalElements(),
                pageTeachers.getTotalPages(),
                pageTeachers.getNumber(),
                pageTeachers.getSize()
        );
    }

    public void update(@Valid UpdateTeacherSubjectRequest dto) {
        TeachersEntity teacher = teacherRepository.findById(dto.id())
                .orElseThrow(() -> new CustomNotFoundException("Professor não encontrado"));

        if(dto.subjectsId().isEmpty()){
            throw new UpdateTeacherSubjectException("Disciplina não encontrada");
        }

        dto.subjectsId().forEach(subject -> subjectRepository.findById(subject.id())
                .orElseThrow(() -> new CustomNotFoundException("Disciplina não encontrada")));

        dto.subjectsId().forEach(subject ->
                teacher.addSubject(subjectRepository.getReferenceById(subject.id()))
        );

        teacherRepository.save(teacher);
    }
}
