package vitorcode.school_proof_corrector.core.domain.model

data class PageResponse<T>(
    val items: List<T>,
    val totalItems: Long,
    val totalPages: Int,
    val currentPage: Int,
    val pageSize: Int
)
