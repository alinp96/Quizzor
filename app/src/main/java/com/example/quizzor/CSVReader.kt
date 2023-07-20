package com.example.quizzor

import android.content.Context
import android.util.Log

class CSVReader {
    fun readCSVData(context: Context): List<TFQuestion> {
        val questionList = mutableListOf<TFQuestion>()

        try {
            context.assets.open("generalCultureData.csv").bufferedReader().useLines { lines ->
                // Skip the first line since it contains headers
                val dataLines = lines.drop(1)
                for (line in dataLines) {
                    val data = line.split(", ")
                    val question = data[0]
                    val answer = data[1].toBoolean()
                    val fullQuestion = TFQuestion(question, answer)
                    questionList.add(fullQuestion)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Log.d("TAG", questionList.toString())
        return questionList
    }
}