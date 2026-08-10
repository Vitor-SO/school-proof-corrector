package vitorcode.school_proof_corrector.data.repositories

import vitorcode.school_proof_corrector.core.domain.model.PageResponse
import vitorcode.school_proof_corrector.core.domain.model.SubjectResponse
import vitorcode.school_proof_corrector.core.domain.model.SubjectResponseFilter
import vitorcode.school_proof_corrector.data.local.TokenManager
import vitorcode.school_proof_corrector.data.remote.SubjectApi
import javax.inject.Inject

class SubjectRepositoryImpl @Inject constructor(
    private val subjectApi: SubjectApi,
    private val tokenManager: TokenManager
): ISubjectRepository {
    override suspend fun read(filter: SubjectResponseFilter?): PageResponse<SubjectResponse> {
        return subjectApi.read(filter)
    }

    override suspend fun getSubjectsByTeacherId(): PageResponse<SubjectResponse> {
        return subjectApi.read(SubjectResponseFilter(teacherId = tokenManager.getUserId()))
    }

}
