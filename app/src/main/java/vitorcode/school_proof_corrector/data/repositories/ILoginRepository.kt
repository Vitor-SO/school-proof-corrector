package vitorcode.school_proof_corrector.data.repositories

import vitorcode.school_proof_corrector.core.domain.model.LoginResponse

interface ILoginRepository {
    suspend fun login(
        slug: String,
        userEmail: String,
        password: String
    ): LoginResponse
}