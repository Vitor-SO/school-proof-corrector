package vitorcode.school_service.dtos;

import java.util.List;

public record PageResponse<T>(
        List<T> items,
        long totalItems,
        int totalPages,
        int currentPage,
        int pageSize
) {
}
