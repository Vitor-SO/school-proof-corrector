package vitorcode.school_proof_corrector.core.ui.events

sealed class BulletinEvent {
    data object NavigateToCreateBulletin : BulletinEvent()
}
