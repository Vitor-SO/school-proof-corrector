package vitorcode.tenant_service.database.master.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import vitorcode.tenant_service.enums.TenantDatabaseStatus;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tenants")
public class TenantsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column( unique = true, nullable = false, name = "school_name")
    private String schoolName;
    @Column( unique = true, nullable = false, name = "db_name")
    private String dbName;
    @Column( unique = true, nullable = false, name = "db_user")
    private String dbUser;
    @Column( unique = true, nullable = false, name = "db_password")
    private String dbPassword;
    @Enumerated(EnumType.STRING)
    private TenantDatabaseStatus status;
    @Column(nullable = false, name = "created_at")
    @CreationTimestamp
    private Instant createdAt;
    @Column(nullable = false, name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public TenantDatabaseStatus getStatus() {
        return status;
    }

    public void setStatus(TenantDatabaseStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
