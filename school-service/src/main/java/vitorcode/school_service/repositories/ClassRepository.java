package vitorcode.school_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vitorcode.school_service.database.entities.ClassEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, UUID>,
        JpaSpecificationExecutor<ClassEntity> {
    Optional<ClassEntity> findByName(String name);
}
