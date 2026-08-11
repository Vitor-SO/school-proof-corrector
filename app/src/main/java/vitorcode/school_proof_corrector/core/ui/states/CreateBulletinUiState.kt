package vitorcode.school_proof_corrector.core.ui.states

import android.net.Uri
import java.util.UUID

data class CreateBulletinUiState(
    val frontImageUri: Uri? = null,
    val backImageUri: Uri? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
