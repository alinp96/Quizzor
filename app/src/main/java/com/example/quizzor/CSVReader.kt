package com.example.quizzor

import android.content.Context
import android.util.Log

class CSVReader {

    private fun selectedCSV(category: String, language: String): String{
        var filename: String = ""
        when(language){
            "en"->{
                when(category){
                    "General Culture"-> filename = "generalCultureData_en.csv"
                    "Food and Drink"-> filename = "foodAndDrinkData_en.csv"
                    "Video Games"-> filename = "gamingData_en.csv"
                }
            }
            "ro"->{
                when(category){
                    "Cultura Generala"-> filename = "generalCultureData_ro.csv"
                    "Mancare si Bauturi"-> filename = "foodAndDrinkData_ro.csv"
                    "Jocuri Video"-> filename = "gamingData_ro.csv"
                }
            }
        }
        return filename
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