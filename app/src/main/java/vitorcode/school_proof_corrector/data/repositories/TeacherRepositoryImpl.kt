package vitorcode.school_proof_corrector.data.repositories

import vitorcode.school_proof_corrector.core.domain.model.SubjectResponse
import vitorcode.school_proof_corrector.data.local.TokenManager
import vitorcode.school_proof_corrector.data.remote.TeacherApi
import javax.inject.Inject

class TeacherRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager,
    private val teacherApi: TeacherApi
): ITeacherRepository {
    override suspend fun getSubjects(): List<SubjectResponse> {
        val userId = tokenManager.getUserId()
        return teacherApi.read(userId).items.flatMap { it.subjectResponse ?: emptyList() }
    }
}
