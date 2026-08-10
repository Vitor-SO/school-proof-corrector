package vitorcode.school_proof_corrector.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import vitorcode.school_proof_corrector.core.domain.model.PageResponse
import vitorcode.school_proof_corrector.core.domain.model.SubjectResponse
import vitorcode.school_proof_corrector.core.domain.model.SubjectResponseFilter
import java.util.UUID

interface SubjectApi {
    @GET("subject/read")
    suspend fun read(
        @Query("teacherId") teacherId: SubjectResponseFilter? = null
    ): PageResponse<SubjectResponse>
}
