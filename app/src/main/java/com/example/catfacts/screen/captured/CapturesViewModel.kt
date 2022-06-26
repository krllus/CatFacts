package com.example.catfacts.screen.captured

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catfacts.data.CapturesRepository
import com.example.catfacts.data.model.Capture
import com.example.catfacts.utils.result.Result
import com.example.catfacts.utils.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CapturesViewModel @Inject constructor(
    private val capturesRepository: CapturesRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(CaptureScreenUiState(capturesState = CaptureUiState.Loading))

    val uiState: StateFlow<CaptureScreenUiState> = _uiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CaptureScreenUiState(capturesState = CaptureUiState.Loading)
        )

    private val captures: Flow<Result<List<Capture>>> = capturesRepository.getCaptures().asResult()

    init {
        viewModelScope.launch {
            capturesRepository.getCaptures().asResult().collect { result ->
                val captureState = when (result) {
                    is Result.Success -> CaptureUiState.Success(result.data)
                    is Result.Error -> CaptureUiState.Error(
                        result.exception ?: Exception("Failed to get captures")
                    )
                    is Result.Loading -> CaptureUiState.Loading
                }

                _uiState.value = CaptureScreenUiState(captureState)

            }
        }
    }

    fun addRandomCapture() {
        viewModelScope.launch {
            capturesRepository.addRandomCapture()
        }
    }

}

sealed interface CaptureUiState {
    data class Success(val captures: List<Capture>) : CaptureUiState
    data class Error(val error: Throwable) : CaptureUiState
    object Loading : CaptureUiState
}

data class CaptureScreenUiState(
    val capturesState: CaptureUiState
)
