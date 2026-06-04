package vitorcode.tenant_service.repositories.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vitorcode.tenant_service.database.master.entities.MasterUsersEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MasterRepository extends JpaRepository<MasterUsersEntity, UUID> {
    Optional<MasterUsersEntity> findByUsername(String username);
}
