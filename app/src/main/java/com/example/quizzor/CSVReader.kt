package com.example.quizzor

import android.content.Context

class CSVReader {

    private fun selectedCSV(category: String, language: String): String{
        var filename: String = ""
        when(language){
            "en"->{
                when(category){
                    "Trivia"-> filename = "trivia_en.csv"
                    "World Facts"-> filename = "worldFacts_en.csv"
                    "Famous Personalities"-> filename = "famousPersonalities_en.csv"
                    "Physics"-> filename = "physics_en.csv"
                    "Chemistry"-> filename = "chemistry_en.csv"
                    "Biology"-> filename = "biology_en.csv"
                    "Computer Science"-> filename = "computerScience_en.csv"
                    "Countries and Captitals"-> filename = "countriesAndCapitals_en.csv"
                    "Geographic facts and Trivia"-> filename = "geographicFactsAndTrivia_en.csv"
                    "Famous Historical Figures"-> filename = "famousHistoricalFigures_en.csv"
                    "Historical Events"-> filename = "historicalEvents_en.csv"
                    "Movies"-> filename = "movies_en.csv"
                    "Tv Shows"-> filename = "tvShows_en.csv"
                    "Music"-> filename = "music_en.csv"
                    "Books and Literature"-> filename = "booksAndLiterature_en.csv"
                    "Celebrity and Pop Culture"-> filename = "celebrityAndCulture_en.csv"
                    "Video Games"-> filename = "videoGames_en.csv"
                    "Soccer"-> filename = "soccer_en.csv"
                    "Unusual sports facts"-> filename = "unusualSportsFacts_en.csv"
                    "Memorable sporting events"-> filename = "memorableSportingEvents_en.csv"
                    "Cuisine"-> filename = "cuisine_en.csv"
                    "Ingredients"-> filename = "ingredients_en.csv"
                    "Beverages"-> filename = "beverages_en.csv"
                    "Food and Trivia"-> filename = "foodTrivia_en.csv"
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