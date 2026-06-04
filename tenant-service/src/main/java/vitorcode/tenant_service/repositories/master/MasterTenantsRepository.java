package vitorcode.tenant_service.repositories.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vitorcode.tenant_service.database.master.entities.TenantsEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MasterTenantsRepository extends JpaRepository<TenantsEntity, UUID> {
    Optional<TenantsEntity> findByDbName(String slug);
}
