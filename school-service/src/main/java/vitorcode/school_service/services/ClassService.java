package vitorcode.school_service.services;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import vitorcode.school_service.database.entities.ClassEntity;
import vitorcode.school_service.dtos.CreateClassRequest;
import vitorcode.school_service.exceptions.AlreadyExistsException;
import vitorcode.school_service.repositories.ClassRepository;

@Service
public class ClassService {
    private final ClassRepository schoolRepository;

    public ClassService(ClassRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public void create(@Valid CreateClassRequest dto) {
        try {
            boolean classNameAlreadyExists = schoolRepository.findByName(dto.name()).isPresent();
            if(classNameAlreadyExists){
                throw new AlreadyExistsException("O nome da escola já existe");
            }
            ClassEntity entity = new ClassEntity();
            entity.setName(dto.name());
            schoolRepository.save(entity);
        }catch (AlreadyExistsException sna){
            throw sna;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
