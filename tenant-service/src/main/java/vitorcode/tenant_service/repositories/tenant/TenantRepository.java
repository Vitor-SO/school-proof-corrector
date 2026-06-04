package vitorcode.tenant_service.repositories.tenant;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import vitorcode.tenant_service.config.providers.MasterDatasourceProvider;
import vitorcode.tenant_service.config.providers.TenantDatasourceProvider;
import vitorcode.tenant_service.database.master.entities.TenantsEntity;
import vitorcode.tenant_service.database.tenant.entities.Roles;
import vitorcode.tenant_service.database.tenant.entities.TenantUsersEntity;
import vitorcode.tenant_service.dtos.CreateTenantRequest;
import vitorcode.tenant_service.dtos.LoginTenantUserRequest;
import vitorcode.tenant_service.dtos.LoginUserResponse;
import vitorcode.tenant_service.enums.TenantDatabaseStatus;
import vitorcode.tenant_service.exceptions.*;
import vitorcode.tenant_service.utils.AesCryptoProvider;
import vitorcode.tenant_service.utils.PasswordGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

@Repository
public class TenantRepository {
    private final HikariDataSource dataSource;
    private final PasswordGenerator passwordGenerator;
    private String generatedUserPassword;
    private final TenantDatasourceProvider tenantDatasourceProvider;
    private final MasterDatasourceProvider masterDatasourceProvider;
    private final AesCryptoProvider aesCryptoProvider;

    public TenantRepository(@Qualifier("masterDataSource") HikariDataSource dataSource, PasswordGenerator passwordGenerator, TenantDatasourceProvider tenantDatasourceProvider, MasterDatasourceProvider masterDatasourceProvider, AesCryptoProvider aesCryptoProvider) {
        this.dataSource = dataSource;
        this.passwordGenerator = passwordGenerator;
        this.tenantDatasourceProvider = tenantDatasourceProvider;
        this.masterDatasourceProvider = masterDatasourceProvider;
        this.aesCryptoProvider = aesCryptoProvider;
    }

    public void createTenant(CreateTenantRequest dto){
        try(Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement()){
            conn.setAutoCommit(true);
            stmt.execute(
                    String.format("CREATE DATABASE %s ENCODING 'UTF-8' TEMPLATE template0",
                            dto.slug())
            );
        }catch (Exception e){
            throw new RuntimeException("Error creating tenant: "+ e.getMessage(), e);
        }
    }

    public void setGeneratedUserPassword(String generatedUserPassword) {
        this.generatedUserPassword = generatedUserPassword;
    }

    public String getGeneratedUserPassword() {
        return generatedUserPassword;
    }

    public void createUserDb(CreateTenantRequest dto) {
        String password = passwordGenerator.generate();
        this.setGeneratedUserPassword(password);
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            // Cria o usuário do tenant
            stmt.execute(String.format("""
            CREATE USER %s WITH PASSWORD '%s';
            """, dto.slug(), password));

            // Permissão mínima de conexão ao banco do tenant
            stmt.execute(String.format("""
            GRANT CONNECT ON DATABASE %s TO %s;
            """, dto.slug(), dto.slug()));

        } catch (Exception e) {
            // ROLLBACK MANUAL (compensação)
            try (Connection connRollback = dataSource.getConnection();
                 Statement stmtRollback = connRollback.createStatement()) {

                stmtRollback.execute("DROP DATABASE IF EXISTS " + dto.slug());
                stmtRollback.execute("DROP USER IF EXISTS " + dto.slug());
            } catch (Exception ex) {
                throw new RuntimeException("Erro tentando fazer rollback da criação do tenant", ex);
            }

            throw new RuntimeException("Erro criando usuário do tenant", e);
        }
    }

    public void grantPermissionToUser(CreateTenantRequest dto){
        HikariDataSource ds1 = tenantDatasourceProvider.getHikariDataSource(dto.slug(),masterDatasourceProvider.getUsername(),masterDatasourceProvider.getPassword());
        try(Connection conn = ds1.getConnection();
            Statement stmt = conn.createStatement()) {
            stmt.execute(String.format("""
                    GRANT USAGE, CREATE ON SCHEMA public TO %s;
                    """, dto.slug()));
            ds1.close();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conceder permissão usando master ao usuário do tenant", e);
        }

        HikariDataSource ds2 = tenantDatasourceProvider.getHikariDataSource(dto.slug(),dto.slug(),
                this.getGeneratedUserPassword());
        try (Connection conn = ds2.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(String.format("""
            ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT DELETE, UPDATE, INSERT, SELECT ON TABLES TO %s;
            """,dto.slug()));
            ds2.close();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conceder permissão ao usuário do tenant", e);
        }
    }

    public void insertUserDb(CreateTenantRequest dto) {
        String passwordEncripted = new BCryptPasswordEncoder().encode(dto.userPassword());
        HikariDataSource ds1 = tenantDatasourceProvider.getHikariDataSource(
                        dto.slug(),
                        dto.slug(),
                        this.getGeneratedUserPassword()
                );
        UUID roleId = null;
        try(Connection conn = ds1.getConnection();
            Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT id FROM roles r WHERE r.name = 'admin'");
            if(rs.next()){
                roleId = (UUID) rs.getObject("id");
            }

            if(roleId == null){
                throw new RoleIdForUserInsertIsNullException("nao foi possivel pegar id da role para inserir no user");
            }

            ds1.close();
        } catch (Exception e) {
            throw new RuntimeException("[M-tenant.repo.inserUserDb] erro de statement", e);
        }

        try (HikariDataSource ds2 = tenantDatasourceProvider.getHikariDataSource(
                dto.slug(),
                dto.slug(),
                this.getGeneratedUserPassword()
        );
             Connection conn = ds2.getConnection();

             PreparedStatement stmt =
                     conn.prepareStatement(
                             """
                                     INSERT INTO users (
                                         name,
                                         email,
                                         password,
                                         role_id
                                     )
                                     VALUES (?, ?, ?, ?)
                                     """
                     )
        ) {

            stmt.setString(1, dto.name());

            stmt.setString(2, dto.userEmail());

            stmt.setString(3, passwordEncripted);

            stmt.setObject(4, roleId);

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new UserSqlInsertDbException("[M-tenant.repo.inserUserDb] Erro ao inserir usuário: " + e.getMessage());
        }
    }

    public void insertTenantConnectionIntoMaster(CreateTenantRequest dto) {
        String passwordEncripted = aesCryptoProvider.encrypt(this.getGeneratedUserPassword());
        try(Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement()) {
            stmt.execute(String.format("""
                    INSERT INTO tenants (school_name,db_name,db_user,db_password,status) VALUES ('%s', '%s', '%s', '%s', '%s');
                    """, dto.name(), dto.slug(), dto.slug(), passwordEncripted, TenantDatabaseStatus.ACTIVE));
        } catch (Exception e) {
            throw new MasterSqlTenantInsertDbException("Erro ao inserir dados do tenant na master: "+ e.getMessage());
        }
    }

    public void insertSchool(CreateTenantRequest dto) {
        try (HikariDataSource ds = tenantDatasourceProvider.getHikariDataSource(
                dto.slug(),
                dto.slug(),
                this.getGeneratedUserPassword()
        );
             Connection conn = ds.getConnection();

             PreparedStatement stmt =
                     conn.prepareStatement(
                             """
                                     INSERT INTO school (
                                         name,
                                         slug,
                                         email
                                     )
                                     VALUES (?, ?, ?)
                                     """
                     )
        ) {

            stmt.setString(1, dto.schoolName());

            stmt.setString(2, dto.slug());

            stmt.setString(3, dto.schoolEmail());

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new SchoolSqlInsertDbException("[M-tenant.repo.insertSchool] Erro ao inserir escola: " + e.getMessage());
        }
    }

    public void deleteTenant(CreateTenantRequest dto) {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(
                    "DROP DATABASE IF EXISTS " + dto.slug() + " WITH (FORCE)"
            );

            stmt.execute(
                    "DROP USER IF EXISTS " + dto.slug()
            );

        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar tenant", e);
        }
    }


    public LoginUserResponse findByUserEmail(
            TenantsEntity tenantDatabaseConnection,
            LoginTenantUserRequest dto
    ) {
        String passwordDesincrypted = aesCryptoProvider.decrypt(tenantDatabaseConnection.getDbPassword());
        try (
                HikariDataSource ds =
                        tenantDatasourceProvider.getHikariDataSource(
                                tenantDatabaseConnection.getDbName(),
                                tenantDatabaseConnection.getDbUser(),
                                passwordDesincrypted
                        );

                Connection conn = ds.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement("""
                            SELECT
                                u.id,
                                u.email,
                                u.password,
                                r.name AS role
                            FROM users u
                            JOIN roles r
                                ON r.id = u.role_id
                            WHERE u.email = ?
                            """)
        ) {

            stmt.setString(1, dto.userEmail());

            try (ResultSet rs = stmt.executeQuery()) {

                if (!rs.next()) {
                    throw new UsernameNotFoundException(
                            "[M-tenant.repo.findByUserEmail] Usuário não encontrado"
                    );
                }

                return new LoginUserResponse(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }

        } catch (UsernameNotFoundException ex) {
            throw ex;

        } catch (Exception e) {

            throw new TenantSqlSelectUserByEmailException(
                    "[M-tenant.repo.findByUserEmail] Erro ao executar a funcao: "
                            + e.getMessage()
            );
        }
    }
}
