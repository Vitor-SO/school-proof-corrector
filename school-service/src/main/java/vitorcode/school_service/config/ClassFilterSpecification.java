package vitorcode.school_service.config;

import org.springframework.data.jpa.domain.Specification;
import vitorcode.school_service.database.entities.ClassEntity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class ClassFilterSpecification {
    public static Specification<ClassEntity> filter(ClassFilter filter) {
        return (root, query, cb) -> {
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
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
