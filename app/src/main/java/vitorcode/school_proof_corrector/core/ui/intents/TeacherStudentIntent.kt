package vitorcode.school_proof_corrector.core.ui.intents

sealed interface TeacherStudentIntent {
    data object LoadStudents : TeacherStudentIntent
}