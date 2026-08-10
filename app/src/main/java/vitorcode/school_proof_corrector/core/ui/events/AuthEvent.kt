package vitorcode.school_proof_corrector.core.ui.events

sealed interface AuthEvent {
    data object NavigateToTeacherScreen : AuthEvent
    data object NavigateToStudentScreen : AuthEvent
}
