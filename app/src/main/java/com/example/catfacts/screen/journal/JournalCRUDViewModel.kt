package com.example.catfacts.screen.journal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catfacts.data.JournalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class JournalCRUDViewModel @Inject constructor(
    private val journalRepository: JournalRepository
) : ViewModel() {

    private val _uiCRUDState =
        MutableStateFlow(JournalCRUDUiState(state = JournalCRUDState.Editing))

    private val _pictureFile = MutableLiveData<File?>()
    val pictureFile: LiveData<File?> = _pictureFile

    private val _pictureCaptureStatus = MutableLiveData<PictureCaptureStatus>()
    val pictureCaptureStatus: LiveData<PictureCaptureStatus> = _pictureCaptureStatus

    val uiCRUDState: StateFlow<JournalCRUDUiState> = _uiCRUDState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = JournalCRUDUiState(state = JournalCRUDState.Editing)
        )


    fun setPictureFile(file: File?) {
        _pictureFile.value = file
    }

    fun resetPicture() {
        _pictureFile.value = null
        changePictureCaptureStatus(PictureCaptureStatus.Idle)
    }

    fun changePictureStatusToCaptured() {
        changePictureCaptureStatus(PictureCaptureStatus.Captured)
    }

    fun changePictureStatusToError() {
        changePictureCaptureStatus(PictureCaptureStatus.Error)
    }

    private fun changePictureCaptureStatus(status: PictureCaptureStatus) {
        _pictureCaptureStatus.value = status
    }

    fun saveJournal(title: String, description: String) {
        viewModelScope.launch {

            try {
                _uiCRUDState.value = JournalCRUDUiState(state = JournalCRUDState.Saving)

                if (title.isEmpty()) {
                    throw Exception("Title is empty")
                }

                // TODO validate picture
                val pictureAbsolutePath = _pictureFile.value?.absolutePath

                journalRepository.saveJournal(title, description, pictureAbsolutePath)

                _uiCRUDState.value = JournalCRUDUiState(state = JournalCRUDState.Saved)
            } catch (e: Exception) {
                _uiCRUDState.value = JournalCRUDUiState(state = JournalCRUDState.Error(e))
            }
        }
    }

    fun resetCrudState() {
        _uiCRUDState.value = JournalCRUDUiState(state = JournalCRUDState.Editing)
    }

}


sealed interface JournalCRUDState {
    object Editing : JournalCRUDState
    data class Error(val error: Throwable) : JournalCRUDState
    object Saving : JournalCRUDState
    object Saved : JournalCRUDState
}

data class JournalCRUDUiState(
    val state: JournalCRUDState
)

sealed class PictureCaptureStatus {
    object Idle : PictureCaptureStatus()
    object Capturing : PictureCaptureStatus()
    object Captured : PictureCaptureStatus()
    object Error : PictureCaptureStatus()
}