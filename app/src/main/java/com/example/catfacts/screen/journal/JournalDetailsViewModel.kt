package com.example.catfacts.screen.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catfacts.data.JournalRepository
import com.example.catfacts.data.model.Journal
import com.example.catfacts.utils.result.Result
import com.example.catfacts.utils.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JournalDetailsViewModel @Inject constructor(
    private val journalRepository: JournalRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(JournalDetailsScreenUiState(state = JournalDetailsUiState.Loading))

    val uiState: StateFlow<JournalDetailsScreenUiState> = _uiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = JournalDetailsScreenUiState(state = JournalDetailsUiState.Loading)
        )

    fun setJournalId(journalId: Long) {
        viewModelScope.launch {
            journalRepository.getJournal(journalId).asResult().collect { result ->
                val state = when (result) {
                    is Result.Success -> JournalDetailsUiState.Success(result.data)
                    is Result.Error -> JournalDetailsUiState.Error(
                        result.exception ?: Exception("Failed to get journal")
                    )
                    is Result.Loading -> JournalDetailsUiState.Loading
                }
                _uiState.value = JournalDetailsScreenUiState(state)
            }
        }
    }


}

sealed interface JournalDetailsUiState {
    data class Success(val journal: Journal?) : JournalDetailsUiState
    data class Error(val error: Throwable) : JournalDetailsUiState
    object Loading : JournalDetailsUiState
}

data class JournalDetailsScreenUiState(
    val state: JournalDetailsUiState
)