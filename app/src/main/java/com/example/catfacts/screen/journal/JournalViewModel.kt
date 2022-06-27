package com.example.catfacts.screen.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catfacts.data.JournalRepository
import com.example.catfacts.data.model.Journal
import com.example.catfacts.utils.result.Result
import com.example.catfacts.utils.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(
    private val journalRepository: JournalRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(JournalScreenUiState(journalsState = JournalUiState.Loading))

    val uiState: StateFlow<JournalScreenUiState> = _uiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = JournalScreenUiState(journalsState = JournalUiState.Loading)
        )

    private val captures: Flow<Result<List<Journal>>> = journalRepository.getJournals().asResult()

    init {
        viewModelScope.launch {
            journalRepository.getJournals().asResult().collect { result ->
                val captureState = when (result) {
                    is Result.Success -> JournalUiState.Success(result.data)
                    is Result.Error -> JournalUiState.Error(
                        result.exception ?: Exception("Failed to get captures")
                    )
                    is Result.Loading -> JournalUiState.Loading
                }

                _uiState.value = JournalScreenUiState(captureState)

            }
        }
    }

    fun addRandomCapture() {
        viewModelScope.launch {
            journalRepository.addRandomJournal()
        }
    }

}

sealed interface JournalUiState {
    data class Success(val journals: List<Journal>) : JournalUiState
    data class Error(val error: Throwable) : JournalUiState
    object Loading : JournalUiState
}

data class JournalScreenUiState(
    val journalsState: JournalUiState
)
