package vitorcode.school_proof_corrector.core.ui.events

sealed interface SignInUiEvent {
    data object NavigateToLogin : SignInUiEvent
}
