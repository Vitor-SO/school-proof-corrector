package vitorcode.school_service.config;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import vitorcode.school_service.database.entities.StudentsEntity;
import vitorcode.school_service.dtos.StudentFilter;

import java.util.ArrayList;
import java.util.List;

public class StudentFilterSpecification {

    public static Specification<StudentsEntity> filter(StudentFilter filter) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filter.name() != null && !filter.name().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("name")),
                                "%" + filter.name().toLowerCase() + "%"
                        )
                );
            }

            if (filter.className() != null && !filter.className().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("className")),
                                "%" + filter.className().toLowerCase() + "%"
                        )
                );
            }

            if (filter.academicLevel() != null && !filter.academicLevel().isBlank()) {
                predicates.add(
                        cb.equal(root.get("academicLevel"), filter.academicLevel())
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}