package vitorcode.school_proof_corrector.core.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import vitorcode.school_proof_corrector.core.domain.model.SignInRequest
import vitorcode.school_proof_corrector.core.ui.states.SignInUiState
import vitorcode.school_proof_corrector.data.repositories.SignInRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: SignInRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    fun onSchoolNameChange(value: String) {
        _uiState.update {
            it.copy(schoolName = value)
        }
    }

    fun onSlugChange(value: String) {
        _uiState.update {
            it.copy(slug = value)
        }
    }

    fun onSchoolEmailChange(value: String) {
        _uiState.update {
            it.copy(schoolEmail = value)
        }
    }

    fun onNameChange(value: String) {
        _uiState.update {
            it.copy(name = value)
        }
    }

    fun onUserEmailChange(value: String) {
        _uiState.update {
            it.copy(userEmail = value)
        }
    }

    fun onUserPasswordChange(value: String) {
        _uiState.update {
            it.copy(userPassword = value)
        }
    }

    fun signIn() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    loading = true,
                    error = null
                )
            }

            try {
                val response = repository.signIn(
                    SignInRequest(
                        schoolName = _uiState.value.schoolName,
                        slug = _uiState.value.slug,
                        schoolEmail = _uiState.value.schoolEmail,
                        name = _uiState.value.name,
                        userEmail = _uiState.value.userEmail,
                        userPassword = _uiState.value.userPassword
                    )
                )

                val errorBody = if (!response.isSuccessful) {
                    response.errorBody()?.string()
                } else {
                    null
                }

                Log.i(
                    "SignInViewModel",
                    "code=${response.code()}, isSuccessful=${response.isSuccessful}, " +
                        "body=${response.body()}, errorBody=$errorBody"
                )

                _uiState.update {
                    it.copy(
                        loading = false,
                        error = errorBody ?: response.message().takeIf { !response.isSuccessful }
                    )
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()

                Log.e("SignInViewModel", "code=${e.code()}, errorBody=$errorBody")

                _uiState.update {
                    it.copy(
                        loading = false,
                        error = errorBody ?: e.message()
                    )
                }
            } catch (e: Exception) {
                Log.e("SignInViewModel", "error", e)

                _uiState.update {
                    it.copy(
                        loading = false,
                        error = e.message
                    )
                }
            }
        }
    }
}
