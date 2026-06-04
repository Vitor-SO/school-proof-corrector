package vitorcode.school_service.services;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import vitorcode.school_service.database.entities.ClassEntity;
import vitorcode.school_service.dtos.CreateClassRequest;
import vitorcode.school_service.exceptions.AlreadyExistsException;
import vitorcode.school_service.repositories.ClassRepository;

@Service
public class ClassService {
    private final ClassRepository classRepository;

    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public void create(@Valid CreateClassRequest dto) {
        boolean classNameAlreadyExists = classRepository.findByName(dto.name()).isPresent();
        if (classNameAlreadyExists) {
            throw new AlreadyExistsException("O nome da escola já existe");
        }
        ClassEntity entity = new ClassEntity();
        entity.setName(dto.name());
        classRepository.save(entity);
    }
}
