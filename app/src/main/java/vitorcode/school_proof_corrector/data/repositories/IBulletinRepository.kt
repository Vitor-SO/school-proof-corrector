package vitorcode.school_proof_corrector.data.repositories

import okhttp3.MultipartBody

interface IBulletinRepository {
    suspend fun createBulletin(
        frontImage: MultipartBody.Part?,
        backImage: MultipartBody.Part?
    )
}