package com.example.catfacts.screen.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catfacts.data.JournalRepository
import com.example.catfacts.data.model.Journal
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
            try {
                _uiState.value = JournalDetailsScreenUiState(JournalDetailsUiState.Loading)
                val journal = journalRepository.getJournal(journalId)
                _uiState.value = JournalDetailsScreenUiState(JournalDetailsUiState.Success(journal))
            } catch (e: Exception) {
                _uiState.value = JournalDetailsScreenUiState(JournalDetailsUiState.Error(e))
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