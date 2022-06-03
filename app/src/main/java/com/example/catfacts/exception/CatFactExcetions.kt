package com.example.catfacts.exception

class GenericErrorException(val title: String?, message: String?) : Exception(message)