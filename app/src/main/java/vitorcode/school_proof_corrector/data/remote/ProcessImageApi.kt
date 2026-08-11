package vitorcode.school_proof_corrector.data.remote

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProcessImageApi {
    @Multipart
    @POST("gemini/process")
    suspend fun processImage(
        @Part front: MultipartBody.Part?,
        @Part back: MultipartBody.Part?
    )
}
