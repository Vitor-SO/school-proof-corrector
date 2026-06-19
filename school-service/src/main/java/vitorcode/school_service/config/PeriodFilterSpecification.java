package vitorcode.school_service.config;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import vitorcode.school_service.database.entities.PeriodEntity;
import vitorcode.school_service.dtos.PeriodFilter;

import java.util.ArrayList;
import java.util.List;

public class PeriodFilterSpecification {
    public static Specification<PeriodEntity> filter(PeriodFilter filter) {
        return (root, query, cb) ->{
            List<Predicate> predicates = new ArrayList<>();

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
