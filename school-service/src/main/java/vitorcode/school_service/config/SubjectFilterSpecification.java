package vitorcode.school_service.config;

import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import vitorcode.school_service.database.entities.SubjectEntity;
import vitorcode.school_service.dtos.SubjectFilter;

import java.util.ArrayList;
import java.util.List;

public class SubjectFilterSpecification {
    private static final Logger log = LoggerFactory.getLogger(SubjectFilterSpecification.class);

    public static Specification<SubjectEntity> filter(SubjectFilter filter){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(filter.name() != null && !filter.name().isBlank()){
                predicates.add(
                        cb.like(
                                cb.lower(root.get("name")),
                                "%" + filter.name().toLowerCase() + "%"
                        )
                );
            }

            if(filter.teacherId() != null){
                predicates.add(
                        cb.equal(root.get("teacher").get("id"), filter.teacherId())
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
