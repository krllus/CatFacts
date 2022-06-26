package com.example.catfacts.screen.fact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catfacts.data.model.Fact
import com.example.catfacts.data.FactRepository
import com.example.catfacts.data.remote.Result
import com.example.catfacts.utils.ErrorMessage
import com.example.catfacts.utils.Event
import com.example.catfacts.utils.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactsViewModel @Inject constructor(
    private val factRepository: FactRepository
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _fact = MutableLiveData<Event<Fact>>()
    val fact: LiveData<Event<Fact>>
        get() = _fact

    private val _message = MutableLiveData<Event<Message>>()
    val message: LiveData<Event<Message>>
        get() = _message

    fun loadFact() {
        viewModelScope.launch {
            try {
                setLoadingOn()

                when (val result = factRepository.getFact()) {
                    is Result.Success -> {
                        result.value?.let {
                            _fact.value = Event(it)
                        }
                    }
                    is Result.Failure -> {
                        _message.value =
                            Event(ErrorMessage.fromThrowable(result.throwable))
                    }
                }
            } catch (e: Exception) {
                _message.value = Event(ErrorMessage.fromException(e))
            } finally {
                setLoadingOff()
            }
        }
    }

    private fun setLoadingOn() {
        if (_loading.value != true)
            _loading.value = true
    }

    private fun setLoadingOff() {
        if (_loading.value != false)
            _loading.value = false
    }

}