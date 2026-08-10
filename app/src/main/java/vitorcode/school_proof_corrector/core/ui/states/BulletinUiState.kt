package vitorcode.school_proof_corrector.core.ui.states

import java.util.UUID

data class BulletinUiState(
    val studentId: UUID = UUID(0,0),
    val isLoading: Boolean = false,
    val error: String? = null
)
