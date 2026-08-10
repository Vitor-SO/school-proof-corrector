package vitorcode.school_proof_corrector.core.domain.model

data class SignInRequest(
    val schoolName: String,
    val slug: String,
    val schoolEmail: String,
    val name: String,
    val userEmail: String,
    val userPassword: String
)
