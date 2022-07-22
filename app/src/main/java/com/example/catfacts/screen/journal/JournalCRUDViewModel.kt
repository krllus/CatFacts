package com.example.catfacts.screen.journal

import androidx.lifecycle.*
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
    private val savedStateHandle: SavedStateHandle,
    private val journalRepository: JournalRepository
) : ViewModel() {


    private val _uiCRUDState =
        MutableStateFlow(JournalCRUDUiState(state = JournalCRUDState.Editing))

    val pictureFilePath: LiveData<String?> = savedStateHandle.getLiveData(
        PICTURE_PATH_SAVED_STATE_KEY
    )

    private val pictureCaptureStatusValue: LiveData<Int> = savedStateHandle.getLiveData(
        PICTURE_CAPTURE_STATUS_STATE_KEY
    )

    val pictureCaptureStatus: LiveData<PictureCaptureStatus> =
        Transformations.map(pictureCaptureStatusValue) {
            when (it) {
                0 -> PictureCaptureStatus.Idle
                1 -> PictureCaptureStatus.Capturing
                2 -> PictureCaptureStatus.Captured
                else -> PictureCaptureStatus.Error
            }
        }

    val uiCRUDState: StateFlow<JournalCRUDUiState> = _uiCRUDState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = JournalCRUDUiState(state = JournalCRUDState.Editing)
        )


    fun setPictureFile(file: File) {
        savedStateHandle[PICTURE_PATH_SAVED_STATE_KEY] = file.absolutePath
    }

    fun resetPicture() {
        savedStateHandle[PICTURE_PATH_SAVED_STATE_KEY] = null
        changePictureCaptureStatus(PictureCaptureStatus.Idle)
    }

    fun changePictureStatusToCapturing() {
        changePictureCaptureStatus(PictureCaptureStatus.Capturing)
    }

    fun changePictureStatusToCaptured() {
        changePictureCaptureStatus(PictureCaptureStatus.Captured)
    }

    fun changePictureStatusToError() {
        changePictureCaptureStatus(PictureCaptureStatus.Error)
    }

    private fun changePictureCaptureStatus(status: PictureCaptureStatus) {
        savedStateHandle[PICTURE_CAPTURE_STATUS_STATE_KEY] = status.value
    }

    fun saveJournal(title: String, description: String) {
        viewModelScope.launch {

            try {
                _uiCRUDState.value = JournalCRUDUiState(state = JournalCRUDState.Saving)

                if (title.isEmpty()) {
                    throw Exception("Title is empty")
                }

                val pictureFilePath = pictureFilePath.value

                journalRepository.saveJournal(title, description, pictureFilePath)

                _uiCRUDState.value = JournalCRUDUiState(state = JournalCRUDState.Saved)
            } catch (e: Exception) {
                _uiCRUDState.value = JournalCRUDUiState(state = JournalCRUDState.Error(e))
            }
        }
    }

    fun resetCrudState() {
        _uiCRUDState.value = JournalCRUDUiState(state = JournalCRUDState.Editing)
    }

    companion object {
        const val PICTURE_PATH_SAVED_STATE_KEY = "picture_path_saved_state_key"
        const val PICTURE_CAPTURE_STATUS_STATE_KEY = "picture_capture_status_state_key"
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

sealed class PictureCaptureStatus(val value: Int) {
    object Idle : PictureCaptureStatus(0)
    object Capturing : PictureCaptureStatus(1)
    object Captured : PictureCaptureStatus(2)
    object Error : PictureCaptureStatus(3)
}