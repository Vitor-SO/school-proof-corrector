package vitorcode.school_service.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vitorcode.school_service.database.entities.Roles;
import vitorcode.school_service.database.entities.TeacherEntity;
import vitorcode.school_service.dtos.CreateTeacherRequest;
import vitorcode.school_service.exceptions.CreateTeacherException;
import vitorcode.school_service.exceptions.CreateTeacherParamsIsNullException;
import vitorcode.school_service.repositories.AdminTeacherRepository;

@Service
public class AdminTeacherService {
    private final AdminTeacherRepository adminTeacherRepository;

    public AdminTeacherService(AdminTeacherRepository adminTeacherRepository) {
        this.adminTeacherRepository = adminTeacherRepository;
    }

    public void create(CreateTeacherRequest dto){
        try {
            if(dto.name().isEmpty() || dto.email().isEmpty() ||
                    dto.password().isEmpty() || dto.roleId() == null){
                throw new CreateTeacherParamsIsNullException("Verifique os dados enviados");
            }

            TeacherEntity entity = new TeacherEntity();
            entity.setName(dto.name());
            entity.setEmail(dto.email());
            entity.setPassword(new BCryptPasswordEncoder().encode(dto.password()));
            Roles roles = new Roles();
            roles.setId(dto.roleId());
            entity.setRole(roles);

            adminTeacherRepository.save(entity);
        } catch (CreateTeacherParamsIsNullException a){
            throw a;
        }
        catch (Exception e) {
            throw new CreateTeacherException(e);
        }

    }
}
