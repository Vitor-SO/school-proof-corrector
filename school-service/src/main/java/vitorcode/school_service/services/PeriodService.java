package vitorcode.school_service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vitorcode.school_service.config.PeriodFilterSpecification;
import vitorcode.school_service.database.entities.PeriodEntity;
import vitorcode.school_service.dtos.PageResponse;
import vitorcode.school_service.dtos.PeriodFilter;
import vitorcode.school_service.dtos.PeriodResponse;
import vitorcode.school_service.dtos.TeacherResponse;
import vitorcode.school_service.repositories.PeriodRepository;

@Service
public class PeriodService {
    private final PeriodRepository periodRepository;

    public PeriodService(PeriodRepository periodRepository) {
        this.periodRepository = periodRepository;
    }
    public PageResponse<PeriodResponse> read(PeriodFilter filter, Pageable pageable) {
        Page<PeriodEntity> pagePeriods = periodRepository.findAll(PeriodFilterSpecification.filter(filter), pageable);

        return new PageResponse<>(
                pagePeriods.getContent().stream()
                        .map(PeriodResponse::fromEntity)
                        .toList(),
                pagePeriods.getTotalElements(),
                pagePeriods.getTotalPages(),
                pagePeriods.getNumber(),
                pagePeriods.getSize()
        );
    }
}
