package vitorcode.school_proof_corrector.data.repositories

import vitorcode.school_proof_corrector.core.domain.model.PageResponse
import vitorcode.school_proof_corrector.core.domain.model.SubjectResponse
import vitorcode.school_proof_corrector.core.domain.model.SubjectResponseFilter

interface ISubjectRepository {
    suspend fun read(filter: SubjectResponseFilter?): PageResponse<SubjectResponse>
    suspend fun getSubjectsByTeacherId(): PageResponse<SubjectResponse>
}
