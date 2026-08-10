package vitorcode.school_proof_corrector.data.repositories

import vitorcode.school_proof_corrector.core.domain.model.SubjectResponse

interface ITeacherRepository {
    suspend fun getSubjects(): List<SubjectResponse>
}
