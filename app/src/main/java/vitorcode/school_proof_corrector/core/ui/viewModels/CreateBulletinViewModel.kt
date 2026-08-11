package vitorcode.school_proof_corrector.core.ui.viewModels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import vitorcode.school_proof_corrector.core.ui.intents.CreateBulletinIntent
import vitorcode.school_proof_corrector.core.ui.states.CreateBulletinUiState
import vitorcode.school_proof_corrector.data.repositories.BulletinRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class CreateBulletinViewModel @Inject constructor(
    private val bulletinRepository: BulletinRepositoryImpl
): ViewModel() {
    private val _state = MutableStateFlow(CreateBulletinUiState())
    val state = _state.asStateFlow()
    fun onCreateBulletinIntent(intent: CreateBulletinIntent){
        when(intent){
            is CreateBulletinIntent.PhotoFrontCaptured -> {
                _state.update {
                    it.copy(frontImageUri = intent.uri)
                }
            }
            is CreateBulletinIntent.PhotoBackCaptured -> {
                _state.update {
                    it.copy(backImageUri = intent.uri)
                }
            }
        }
    }

    fun onClickCreateBulletin(context: Context) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, error = null)
            }

            try {
                val frontMultipartResponse = _state.value.frontImageUri?.let { uri ->
                    createMultipartFromUri(
                        context = context,
                        imageUri = uri,
                        filename = "front"
                    )
                }
                val backMultipartResponse = _state.value.backImageUri?.let { uri ->
                    createMultipartFromUri(
                        context = context,
                        imageUri = uri,
                        filename = "back"
                    )
                }

                Log.d("CreateBulletinViewModel", "$frontMultipartResponse")

                bulletinRepository.createBulletin(
                    frontImage = frontMultipartResponse,
                    backImage = backMultipartResponse
                )
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = errorBody ?: e.message()
                    )
                }
            }
        }
    }

    private fun createMultipartFromUri(
        context: Context,
        imageUri: Uri,
        filename: String
    ): MultipartBody.Part {
        val inputStream =
            context.contentResolver.openInputStream(imageUri)

        val bytes =
            inputStream?.readBytes()
                ?: throw Exception("Erro ao ler arquivo")

        val requestBody =
            bytes.toRequestBody("image/jpeg".toMediaType())

        return MultipartBody.Part.createFormData(
            filename,
            filename,
            requestBody
        )
    }


}
