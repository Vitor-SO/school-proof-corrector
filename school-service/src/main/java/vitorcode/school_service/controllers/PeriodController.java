package vitorcode.school_service.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vitorcode.school_service.dtos.PageResponse;
import vitorcode.school_service.dtos.PeriodFilter;
import vitorcode.school_service.dtos.PeriodResponse;
import vitorcode.school_service.services.PeriodService;

@RestController
@RequestMapping("/period")
public class PeriodController {
    private final PeriodService periodService;

    public PeriodController(PeriodService periodService) {
        this.periodService = periodService;
    }

    @GetMapping("/read")
    public PageResponse<PeriodResponse> read(
            PeriodFilter filter,
            Pageable pageable
    ) {
        return periodService.read(filter, pageable);
    }
}
