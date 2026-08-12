package vitorcode.school_proof_corrector.data.repositories

import retrofit2.Response
import vitorcode.school_proof_corrector.core.domain.model.SignInRequest
import vitorcode.school_proof_corrector.data.remote.AuthApi
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
): ISignInRepository {

    override suspend fun signIn(
        request: SignInRequest
    ): Response<Unit> {
        return authApi.signIn(request)
    }
}