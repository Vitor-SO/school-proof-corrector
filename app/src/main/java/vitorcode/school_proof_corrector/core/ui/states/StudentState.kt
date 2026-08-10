package vitorcode.school_proof_corrector.core.ui.states

import vitorcode.school_proof_corrector.core.domain.model.PageResponse
import vitorcode.school_proof_corrector.core.domain.model.StudentResponse

data class StudentState(
    val isLoading: Boolean = false,
    val students: PageResponse<StudentResponse>? = null,
    val error: String? = null
)
