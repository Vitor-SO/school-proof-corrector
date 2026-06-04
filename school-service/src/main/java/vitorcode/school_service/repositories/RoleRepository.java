package vitorcode.school_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vitorcode.school_service.database.entities.Roles;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Roles, UUID> {
    Roles findByName(String name);
}
