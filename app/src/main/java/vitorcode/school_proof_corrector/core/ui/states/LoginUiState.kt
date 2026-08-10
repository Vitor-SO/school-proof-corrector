package vitorcode.school_proof_corrector.core.ui.states

data class LoginUiState(
    val slug: String = "",
    val userEmail: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null
)
