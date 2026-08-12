package vitorcode.school_proof_corrector.core.domain.model

import java.util.UUID

data class TeacherResponse (
    val id: UUID,
    val name: String,
    val userId: UUID
)
