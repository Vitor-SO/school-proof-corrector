package vitorcode.school_proof_corrector.core.ui.states

import vitorcode.school_proof_corrector.core.domain.model.PageResponse
import vitorcode.school_proof_corrector.core.domain.model.SubjectResponse

data class SubjectUiState(
    val subjects: PageResponse<SubjectResponse>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
