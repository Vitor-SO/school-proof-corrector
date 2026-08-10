package vitorcode.school_proof_corrector.data.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import vitorcode.school_proof_corrector.core.domain.model.PageResponse
import vitorcode.school_proof_corrector.core.domain.model.StudentResponse
import java.util.UUID

interface StudentApi {
    @GET("student/read")
    suspend fun getStudentsByName(
        @Query("name") name: String? = null
    ): PageResponse<StudentResponse>


    @Multipart
    @POST("student/bulletin")
    suspend fun createBulletin(
        @Part("studentId") studentId: RequestBody,
        @Part("subjectId") subjectId: RequestBody,
        @Part("periodId") periodId: RequestBody,
        @Part front: MultipartBody.Part?,
        @Part back: MultipartBody.Part?
    ): Unit
}
