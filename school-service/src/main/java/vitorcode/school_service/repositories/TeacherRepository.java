package vitorcode.school_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vitorcode.school_service.database.entities.StudentsEntity;
import vitorcode.school_service.database.entities.TeachersEntity;

import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<TeachersEntity, UUID>,
        JpaSpecificationExecutor<TeachersEntity> {
    TeachersEntity findByName(String name);
}
