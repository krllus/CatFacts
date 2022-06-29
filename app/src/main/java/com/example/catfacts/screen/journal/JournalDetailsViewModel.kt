package com.example.catfacts.screen.journal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catfacts.data.JournalRepository
import com.example.catfacts.data.model.Journal
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JournalDetailsViewModel @Inject constructor(
    private val journalRepository: JournalRepository
) : ViewModel() {

    private val _journal = MutableLiveData<Journal>()
    val journal: LiveData<Journal> = _journal


}