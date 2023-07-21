package com.example.quizzor

import android.content.Context
import android.util.Log

class CSVReader {

    private fun selectedCSV(category: String, language: String): String{
        var filename: String = ""
        when(category){
            "generalCulture"-> filename = "generalCultureData_${selectedLanguage(language)}.csv"
            "foodAndDrink"-> filename = "foodAndDrinkData_${selectedLanguage(language)}.csv"
        }
        return filename
    }

    private fun selectedLanguage(language: String): String{
        when(language){
            "english"-> return "en"
            "romana"-> return "ro"
        }
        return "default"
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
        Log.d("TAG", questionList.toString())
        return questionList
    }
}