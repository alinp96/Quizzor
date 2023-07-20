package com.example.quizzor

import kotlin.random.Random

class RandomQuestionIndexes: Random() {
    override fun nextBits(bitCount: Int): Int {
        // Not needed
        return 0
    }

    override fun nextInt(maxNumber: Int): Int {
        return (Math.random() * maxNumber).toInt()
    }
}