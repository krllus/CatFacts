package com.example.catfacts.utils

import com.example.catfacts.exception.GenericErrorException

sealed class Message(val title: String? = "", val body: String)

class SuccessMessage(title: String? = "", body: String?) : Message(title, body ?: "")
class ErrorMessage(title: String? = "", body: String?) : Message(title, body ?: "") {
    companion object {
        fun fromException(exception: Exception): ErrorMessage {
            return when (exception) {
                is GenericErrorException -> ErrorMessage(exception.title, exception.message)
                else -> ErrorMessage(null, exception.message)
            }
        }

        fun fromThrowable(throwable: Throwable): ErrorMessage {
            return when (throwable) {
                is GenericErrorException -> ErrorMessage(throwable.title, throwable.message)
                else -> ErrorMessage(null, throwable.message)
            }
        }
    }
}
