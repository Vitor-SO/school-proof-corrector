package vitorcode.school_proof_corrector.core.ui.states

import vitorcode.school_proof_corrector.core.domain.model.PageResponse
import vitorcode.school_proof_corrector.core.domain.model.StudentResponse
import java.util.UUID

data class TeacherStudentUiState(
    val studentSearched: String = "",
    val studentsPaged: PageResponse<StudentResponse>? = null,
    val studentId: UUID = UUID(0, 0),
    val isLoading: Boolean = false,
    val error: String? = null
)