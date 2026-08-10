package vitorcode.school_proof_corrector.core.ui.states

data class SignInUiState(
    val schoolName: String = "",
    val slug: String = "",
    val schoolEmail: String = "",
    val name: String = "",
    val userEmail: String = "",
    val userPassword: String = "",
    val loading: Boolean = false,
    val error: String? = null
)
