package vitorcode.school_proof_corrector.data.repositories

import android.net.Uri
import okhttp3.MultipartBody
import vitorcode.school_proof_corrector.core.domain.model.PageResponse
import vitorcode.school_proof_corrector.core.domain.model.StudentResponse
import java.util.UUID

interface IStudentRepository {
    suspend fun getStudents(name: String?): PageResponse<StudentResponse>
    suspend fun createBulletin(
        studentId: UUID,
        subjectId: UUID,
        periodId: UUID,
        frontImage: MultipartBody.Part?,
        backImage: MultipartBody.Part?
    )
}
