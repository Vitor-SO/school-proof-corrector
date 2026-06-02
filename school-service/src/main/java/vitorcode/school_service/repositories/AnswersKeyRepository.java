package vitorcode.school_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vitorcode.school_service.database.entities.AnswersKeyEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnswersKeyRepository extends JpaRepository<AnswersKeyEntity, UUID> {
    List<AnswersKeyEntity> findBySubjectId(UUID subjectId);
}
