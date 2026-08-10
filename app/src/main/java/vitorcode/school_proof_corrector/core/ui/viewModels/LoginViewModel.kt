package vitorcode.school_proof_corrector.core.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.jwt.JWT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vitorcode.school_proof_corrector.core.domain.model.UserRole
import vitorcode.school_proof_corrector.core.ui.events.AuthEvent
import vitorcode.school_proof_corrector.core.ui.states.LoginUiState
import vitorcode.school_proof_corrector.data.local.TokenManager
import vitorcode.school_proof_corrector.data.repositories.AuthRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<AuthEvent>()
    val events = _events.asSharedFlow()

    fun onSlugChange(value: String) {
        _uiState.update {
            it.copy(slug = value)
        }
    }

    fun onEmailChange(value: String) {
        _uiState.update {
            it.copy(userEmail = value)
        }
    }

    fun onPasswordChange(value: String) {
        _uiState.update {
            it.copy(password = value)
        }
    }

    fun login() {
        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    loading = true,
                    error = null
                )
            }

            try {
                val response = repository.login(
                    _uiState.value.slug,
                    _uiState.value.userEmail,
                    _uiState.value.password
                )

                if(response.token.isEmpty()) {
                   _uiState.update {
                        it.copy(
                            loading = false,
                            error = "Login Falhou: Token vazio"
                        )
                    }
                    return@launch
                }

                _uiState.update {
                    it.copy(loading = false)
                }

                tokenManager.saveAccessToken(response.token)

               when(getRoleFromToken((response.token))){
                   UserRole.professor -> {
                       _events.emit(AuthEvent.NavigateToTeacherScreen)
                   }
                     UserRole.aluno -> {
                          _events.emit(AuthEvent.NavigateToStudentScreen)
                     }
                     else -> {
                          _uiState.update {
                            it.copy(
                                 error = "Login Falhou: Role desconhecida"
                            )
                          }
                     }
               }

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        loading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    private fun getRoleFromToken(token: String): UserRole? {
        val jwt = JWT()

        return when(jwt.decodeJwt(token).getClaim("role").asString()) {
            "professor" -> UserRole.professor
            "aluno" -> UserRole.aluno
            else -> null
        }
    }
}