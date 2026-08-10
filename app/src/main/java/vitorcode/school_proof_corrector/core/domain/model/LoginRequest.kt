package vitorcode.school_proof_corrector.core.domain.model

data class LoginRequest(
    val slug: String,
    val userEmail: String,
    val password: String
)
