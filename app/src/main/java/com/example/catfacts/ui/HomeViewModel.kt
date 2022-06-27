package com.example.catfacts.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    private val _pictureFile = MutableLiveData<File?>()

    private val _pictureCaptureStatus = MutableLiveData<PictureCaptureStatus>()
    val pictureCaptureStatus: LiveData<PictureCaptureStatus> = _pictureCaptureStatus

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

}

sealed class PictureCaptureStatus {
    object Idle : PictureCaptureStatus()
    object Capturing : PictureCaptureStatus()
    object Captured : PictureCaptureStatus()
    object Error : PictureCaptureStatus()
}