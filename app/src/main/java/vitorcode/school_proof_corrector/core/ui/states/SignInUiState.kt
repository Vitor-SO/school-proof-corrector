package vitorcode.school_proof_corrector.core.ui.states

data class SignInUiState(
    val slug: String = "",
    val name: String = "",
    val userEmail: String = "",
    val userPassword: String = "",
    val loading: Boolean = false,
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val error: String? = null
)
