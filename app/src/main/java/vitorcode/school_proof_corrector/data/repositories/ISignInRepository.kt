package vitorcode.school_proof_corrector.data.repositories

import retrofit2.Response
import vitorcode.school_proof_corrector.core.domain.model.SignInRequest

interface ISignInRepository {
    suspend fun signIn(
        request: SignInRequest
    ): Response<Unit>
}
