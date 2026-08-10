package vitorcode.school_proof_corrector.core.ui.states

import android.net.Uri
import vitorcode.school_proof_corrector.core.domain.model.PeriodResponse
import vitorcode.school_proof_corrector.core.domain.model.SubjectResponse
import java.util.UUID

data class CreateBulletinUiState(
    val studentId: UUID = UUID(0,0),
    val subjects: List<SubjectResponse> = emptyList(),
    val selectedSubject: SubjectResponse = SubjectResponse(
        id = UUID(0,0),
        name = ""
    ),
    val periods: List<PeriodResponse> = emptyList(),
    val periodSelected: PeriodResponse = PeriodResponse(
        id = UUID(0,0),
        name = ""
    ),
    val frontImageUri: Uri? = null,
    val backImageUri: Uri? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
