package vitorcode.school_service.services;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vitorcode.school_service.config.ClassFilter;
import vitorcode.school_service.config.ClassFilterSpecification;
import vitorcode.school_service.database.entities.ClassEntity;
import vitorcode.school_service.dtos.*;
import vitorcode.school_service.exceptions.AlreadyExistsException;
import vitorcode.school_service.exceptions.CustomNotFoundException;
import vitorcode.school_service.repositories.ClassRepository;

import java.util.UUID;

@Service
public class ClassService {
    private final ClassRepository classRepository;

    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public void create(@Valid CreateClassRequest dto) {
        boolean classNameAlreadyExists = classRepository.findByName(dto.name()).isPresent();
        if (classNameAlreadyExists) {
            throw new AlreadyExistsException("O nome da turma já existe");
        }
        ClassEntity entity = new ClassEntity();
        entity.setName(dto.name());
        classRepository.save(entity);
    }

    public PageResponse<ReadClassResponse> read(ClassFilter filter, Pageable pageable) {
        Page<ClassEntity> page = classRepository.findAll(ClassFilterSpecification.filter(filter), pageable);

        return new PageResponse<>(
                page.getContent().stream()
                        .map(ReadClassResponse::fromEntity)
                        .toList(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize()
        );
    }

    public void update(UUID id, UpdateClassRequest dto) {
        ClassEntity entity = classRepository.findById(id).orElseThrow(() -> new CustomNotFoundException("Turma não encontrada"));
        entity.setName(dto.name());
        classRepository.save(entity);
    }

    public void delete(UUID id) {
        classRepository.findById(id).orElseThrow(() -> new CustomNotFoundException("Turma não encontrada"));
        classRepository.deleteById(id);
    }
}
