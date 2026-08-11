package vitorcode.school_proof_corrector.core.ui.intents

import android.net.Uri

sealed interface CreateBulletinIntent {
    data class PhotoFrontCaptured(val uri: Uri) : CreateBulletinIntent
    data class PhotoBackCaptured(val uri: Uri) : CreateBulletinIntent
}
