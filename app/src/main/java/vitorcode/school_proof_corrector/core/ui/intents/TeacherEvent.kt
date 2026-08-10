package vitorcode.school_proof_corrector.core.ui.intents

sealed class TeacherEvent {
    //onde tem o search
    data object NavigateToStudents : TeacherEvent()
}
