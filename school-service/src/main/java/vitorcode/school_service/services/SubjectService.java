package vitorcode.school_service.services;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import vitorcode.school_service.database.entities.ClassEntity;
import vitorcode.school_service.database.entities.SubjectEntity;
import vitorcode.school_service.dtos.CreateSubjectRequest;
import vitorcode.school_service.exceptions.AlreadyExistsException;
import vitorcode.school_service.exceptions.CustomNotFoundException;
import vitorcode.school_service.repositories.ClassRepository;
import vitorcode.school_service.repositories.SubjectRepository;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final ClassRepository classRepository;

    public SubjectService(SubjectRepository subjectRepository, ClassRepository classRepository) {
        this.subjectRepository = subjectRepository;
        this.classRepository = classRepository;
    }

    public void create(@Valid CreateSubjectRequest dto) {
        try {
            boolean classIdExists = classRepository.findById(dto.classId()).isPresent();
            if(!classIdExists){
                throw new CustomNotFoundException("o id da turma nao foi encontrado");
            }

            boolean subjectNameAlreadyExists = subjectRepository.findByName(dto.name()).isPresent();

            if(subjectNameAlreadyExists){
                throw new AlreadyExistsException("O nome da disciplina ja existe");
            }

            SubjectEntity entity = new SubjectEntity();
            entity.setName(dto.name());
            ClassEntity classEntity = new ClassEntity();
            classEntity.setId(dto.classId());
            entity.setSchoolClass(classEntity);

            subjectRepository.save(entity);
        } catch (CustomNotFoundException | AlreadyExistsException e){
            throw e;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
}
