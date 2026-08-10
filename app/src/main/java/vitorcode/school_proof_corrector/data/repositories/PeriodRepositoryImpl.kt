package vitorcode.school_proof_corrector.data.repositories

import vitorcode.school_proof_corrector.core.domain.model.PageResponse
import vitorcode.school_proof_corrector.core.domain.model.PeriodResponse
import vitorcode.school_proof_corrector.data.remote.PeriodApi
import javax.inject.Inject

class PeriodRepositoryImpl @Inject constructor(
    private val periodApi: PeriodApi
): IPeriodRepository {
    override suspend fun getPeriods(): PageResponse<PeriodResponse> {
        return periodApi.read()
    }
}
