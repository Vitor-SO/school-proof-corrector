package vitorcode.school_proof_corrector.core.domain.model

import java.util.UUID

data class SubjectResponseFilter(
    val id: String? = null,
    val name: String? = null,
    val teacherId: UUID? = null
)
