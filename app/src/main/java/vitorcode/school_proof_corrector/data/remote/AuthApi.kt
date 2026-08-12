package vitorcode.school_proof_corrector.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import vitorcode.school_proof_corrector.core.domain.model.LoginRequest
import vitorcode.school_proof_corrector.core.domain.model.LoginResponse
import vitorcode.school_proof_corrector.core.domain.model.SignInRequest

interface AuthApi {
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("tenant/create")
    suspend fun signIn(
        @Body request: SignInRequest
    ): Response<Unit>
}
