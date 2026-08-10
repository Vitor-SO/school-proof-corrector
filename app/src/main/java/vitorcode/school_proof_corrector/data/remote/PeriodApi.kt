package vitorcode.school_proof_corrector.data.remote

import retrofit2.http.GET
import vitorcode.school_proof_corrector.core.domain.model.PageResponse
import vitorcode.school_proof_corrector.core.domain.model.PeriodResponse

interface PeriodApi {
    @GET("period/read")
    suspend fun read(): PageResponse<PeriodResponse>
}
