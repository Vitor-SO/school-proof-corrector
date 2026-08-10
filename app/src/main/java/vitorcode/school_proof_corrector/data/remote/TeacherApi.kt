package vitorcode.school_proof_corrector.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import vitorcode.school_proof_corrector.core.domain.model.PageResponse
import vitorcode.school_proof_corrector.core.domain.model.TeacherResponse
import java.util.UUID

interface TeacherApi {
    @GET("teacher/read")
    suspend fun read(
        @Query("userId") userId: UUID? = null
    ): PageResponse<TeacherResponse>
}
