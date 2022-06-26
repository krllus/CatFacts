package com.example.catfacts.utils

import kotlin.random.Random

class RandomTextGenerator {


    companion object {

        private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        fun generateRandomText(length: Int = 10): String {
            return (1..length)
                .map { Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")
        }
    }

}