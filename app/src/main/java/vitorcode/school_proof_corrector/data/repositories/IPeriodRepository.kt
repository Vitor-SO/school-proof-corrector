package vitorcode.school_proof_corrector.data.repositories

import vitorcode.school_proof_corrector.core.domain.model.PageResponse
import vitorcode.school_proof_corrector.core.domain.model.PeriodResponse

interface IPeriodRepository {
    suspend fun getPeriods(): PageResponse<PeriodResponse>
}
