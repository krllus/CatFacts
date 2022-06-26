package com.example.catfacts.screen.captured

import androidx.lifecycle.ViewModel
import com.example.catfacts.data.CapturesRepository
import com.example.catfacts.models.Capture
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CapturesViewModel @Inject constructor(
    private val capturesRepository: CapturesRepository
) : ViewModel() {

    val captures = capturesRepository.getCaptures()

}

sealed interface CaptureUiState {
    data class Success(val captures: List<Capture>) : CaptureUiState
    data class Error(val error: Throwable) : CaptureUiState
    object Loading : CaptureUiState
}
