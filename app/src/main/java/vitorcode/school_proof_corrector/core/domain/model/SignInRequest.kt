package vitorcode.school_proof_corrector.core.domain.model

data class SignInRequest(
    val slug: String,
    val name: String,
    val userEmail: String,
    val userPassword: String
)
