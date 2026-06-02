package vitorcode.school_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vitorcode.school_service.database.entities.SchoolGradesEntity;

import java.util.UUID;

@Repository
public interface SchoolGradesRepository extends JpaRepository<SchoolGradesEntity, UUID> {
}
