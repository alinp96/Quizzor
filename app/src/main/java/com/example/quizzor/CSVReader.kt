package com.example.quizzor

import android.content.Context

class CSVReader {

    private fun selectedCSV(category: String, language: String): String {
        var transformedCategoryName: String = category.replace("\\s".toRegex(), "").lowercase()

        return transformedCategoryName + "_" + language + ".csv"
    }

    fun readCSVData(context: Context, category: String, language: String): List<TFQuestion> {
        val questionList = mutableListOf<TFQuestion>()

        try {
            context.assets.open(selectedCSV(category, language)).bufferedReader().useLines { lines ->
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
        return questionList
    }
}