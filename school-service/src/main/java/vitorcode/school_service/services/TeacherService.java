package vitorcode.school_service.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vitorcode.school_service.database.entities.Roles;
import vitorcode.school_service.database.entities.TeachersEntity;
import vitorcode.school_service.database.entities.UsersEntity;
import vitorcode.school_service.dtos.CreateTeacherRequest;
import vitorcode.school_service.exceptions.CreateTeacherException;
import vitorcode.school_service.exceptions.CreateTeacherParamsIsNullException;
import vitorcode.school_service.repositories.RoleRepository;
import vitorcode.school_service.repositories.TeacherRepository;
import vitorcode.school_service.repositories.UsersRepository;

@Service
public class TeacherService {
    private final UsersRepository usersRepository;
    private final TeacherRepository teacherRepository;
    private final RoleRepository roleRepository;

    public TeacherService(UsersRepository usersRepository, TeacherRepository teacherRepository, RoleRepository roleRepository) {
        this.usersRepository = usersRepository;
        this.teacherRepository = teacherRepository;
        this.roleRepository = roleRepository;
    }

    public void create(CreateTeacherRequest dto){
        if (dto.name().isEmpty() || dto.email().isEmpty() ||
                dto.password().isEmpty()) {
            throw new CreateTeacherParamsIsNullException("Verifique os dados enviados");
        }

        Roles role = roleRepository.findByName("professor");
        if (role == null) {
            throw new CreateTeacherException("Role professor not found");
        }


        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setName(dto.name());
        usersEntity.setEmail(dto.email());
        usersEntity.setPassword(new BCryptPasswordEncoder().encode(dto.password()));
        usersEntity.setRole(role);
        usersRepository.save(usersEntity);
        TeachersEntity teachersEntity = new TeachersEntity();
        teachersEntity.setName(dto.name());
        teachersEntity.setUser(usersEntity);
        teacherRepository.save(teachersEntity);

    }
}
