package vitorcode.school_proof_corrector.data.repositories

import android.util.Log
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import vitorcode.school_proof_corrector.core.domain.model.PageResponse
import vitorcode.school_proof_corrector.core.domain.model.StudentResponse
import vitorcode.school_proof_corrector.data.remote.StudentApi
import java.util.UUID
import javax.inject.Inject

class StudentRepositoryImpl @Inject constructor(
    private val studentApi: StudentApi
): IStudentRepository {
    override suspend fun getStudents(name: String?): PageResponse<StudentResponse> {
        return studentApi.getStudentsByName(name)
    }

    override suspend fun createBulletin(
        studentId: UUID,
        subjectId: UUID,
        periodId: UUID,
        frontImage: MultipartBody.Part?,
        backImage: MultipartBody.Part?
    ) {
        val studentIdBody =
            studentId.toString()
                .toRequestBody("text/plain".toMediaType())

        val subjectIdBody =
            subjectId.toString()
                .toRequestBody("text/plain".toMediaType())

        val periodIdBody =
            periodId.toString()
                .toRequestBody("text/plain".toMediaType())
        Log.d("API", "$studentId")
        Log.d("API", "$subjectId")
        Log.d("API", "$periodId")
        Log.d("API", frontImage.toString())
        Log.d("API", backImage.toString())
        studentApi.createBulletin(studentIdBody, subjectIdBody, periodIdBody, frontImage, backImage)
    }
}