package vitorcode.school_proof_corrector.core.config

import okhttp3.Interceptor
import okhttp3.Response
import vitorcode.school_proof_corrector.data.local.TokenManager

class AuthInterceptor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(
        chain: Interceptor.Chain
    ): Response {

        val requestBuilder = chain.request()
            .newBuilder()

        tokenManager.getToken()?.let { token ->
            requestBuilder.addHeader(
                "Authorization",
                "Bearer $token"
            )
        }

        return chain.proceed(
            requestBuilder.build()
        )
    }
}