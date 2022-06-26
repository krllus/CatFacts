package com.example.catfacts.data

import com.example.catfacts.di.IoDispatcher
import com.example.catfacts.models.Capture
import kotlinx.coroutines.CoroutineDispatcher

interface CapturesRepository {
    fun getCaptures(): List<Capture>
}

class DefaultCapturesRepository(
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : CapturesRepository {
    override fun getCaptures(): List<Capture> {

        val capture = Capture("Neko")
        val capture2 = Capture("Neko2")
        val capture3 = Capture("Neko3")
        val capture4 = Capture("Neko4")
        val capture5 = Capture("Neko5")
        val capture6 = Capture("Neko6")
        val capture7 = Capture("Neko7")
        val capture8 = Capture("Neko8")
        val capture9 = Capture("Neko9")


        return listOf(
            capture,
            capture2,
            capture3,
            capture4,
            capture5,
            capture6,
            capture7,
            capture8,
            capture9
        )
    }
}