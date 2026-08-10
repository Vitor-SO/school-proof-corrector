package vitorcode.school_proof_corrector.core.domain.model

import java.util.UUID

data class StudentResponse(
    val id: UUID,
    val name: String,
    val className: String?,
    val academicLevel: String?
)
