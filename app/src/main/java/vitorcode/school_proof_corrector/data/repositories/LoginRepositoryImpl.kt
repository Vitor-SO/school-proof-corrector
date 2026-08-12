package vitorcode.school_proof_corrector.data.repositories

import vitorcode.school_proof_corrector.core.domain.model.LoginRequest
import vitorcode.school_proof_corrector.core.domain.model.LoginResponse
import vitorcode.school_proof_corrector.data.remote.AuthApi
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
): ILoginRepository {
    override suspend fun login(slug: String, userEmail: String, password: String): LoginResponse {
        return authApi.login(
            LoginRequest(
                slug,
                userEmail,
                password
            )
        )
    }
}
