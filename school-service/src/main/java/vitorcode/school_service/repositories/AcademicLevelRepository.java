package vitorcode.school_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vitorcode.school_service.database.entities.AcademicLevelEntity;

import java.util.UUID;

@Repository
public interface AcademicLevelRepository extends JpaRepository<AcademicLevelEntity, UUID> {
}
