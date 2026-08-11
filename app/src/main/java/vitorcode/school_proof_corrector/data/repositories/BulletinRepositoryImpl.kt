package vitorcode.school_proof_corrector.data.repositories

import android.util.Log
import okhttp3.MultipartBody
import vitorcode.school_proof_corrector.data.remote.ProcessImageApi
import javax.inject.Inject

class BulletinRepositoryImpl @Inject constructor(
    private val processImageApi: ProcessImageApi
): IBulletinRepository {
    override suspend fun createBulletin(
        frontImage: MultipartBody.Part?,
        backImage: MultipartBody.Part?
    ) {
        Log.d("BulletinRepositoryImpl", "$frontImage")
        processImageApi.processImage(frontImage, backImage)
    }
}