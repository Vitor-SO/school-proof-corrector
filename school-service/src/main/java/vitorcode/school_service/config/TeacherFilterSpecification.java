package vitorcode.school_service.config;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import vitorcode.school_service.database.entities.TeachersEntity;

import java.util.ArrayList;
import java.util.List;

public class TeacherFilterSpecification {
    public static Specification<TeachersEntity> filter(TeacherFilter filter) {
        return (root, query, cb) ->{
            List<Predicate> predicates = new ArrayList<>();

            if(filter.id() != null){
                predicates.add(
                        cb.equal(root.get("id"), filter.id())
                );
            }

            if (filter.name() != null && !filter.name().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("name")),
                                "%" + filter.name().toLowerCase() + "%"
                        )
                );
            }

            if(filter.subjectId() != null){
                predicates.add(
                        cb.equal(root.get("subjects").get("id"), filter.subjectId())
                );
            }

            if(filter.userId() != null){
                predicates.add(
                        cb.equal(root.get("user").get("id"), filter.userId())
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
