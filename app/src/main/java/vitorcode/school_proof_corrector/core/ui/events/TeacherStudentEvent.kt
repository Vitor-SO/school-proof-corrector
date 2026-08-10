package vitorcode.school_proof_corrector.core.ui.events

sealed class TeacherStudentEvent {
    data object NavigateToBulletin: TeacherStudentEvent()
}
