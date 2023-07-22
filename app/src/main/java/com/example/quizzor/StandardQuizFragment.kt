package com.example.quizzor

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class StandardQuizFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sharedPreferences = requireActivity().getSharedPreferences("userPreferences", Context.MODE_PRIVATE)

        val language = getDataFromSharedPreferences("language")
        val category = getDataFromSharedPreferences("category")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_standard_quiz, container, false)
        val questionTextView = view.findViewById<TextView>(R.id.textViewQuestion)
        val showQuestionNrTextView = view.findViewById<TextView>(R.id.textViewShowQuestion)
        val showScore = view.findViewById<TextView>(R.id.textViewShowScore)

        val btnTrue = view.findViewById<Button>(R.id.btnTrue)
        val btnFalse = view.findViewById<Button>(R.id.btnFalse)
        val btnGoBack = view.findViewById<Button>(R.id.btnGoBack)
        var score: Int = 0
        var questionNr: Int = 0
        var numberOfQuestions: Int = 10
        var currentAnswer: Boolean = false

        setLanguagePreferencesToView(language, view)

        val questionList: List<TFQuestion> = getQuestionList(numberOfQuestions, category, language)

        if(ifGameInProgress(questionNr, numberOfQuestions)){
            questionTextView.text =  showNextQuestion(questionNr, questionList)
            showQuestionNrTextView.text = "Question ${questionNr + 1}/${numberOfQuestions}"
            showScore.text = "Score: ${score}"
            btnTrue.visibility = View.VISIBLE
            btnFalse.visibility = View.VISIBLE
        }

        btnTrue.setOnClickListener{
            if(ifGameInProgress(questionNr, numberOfQuestions)) {
                Log.d("TAG", questionList[questionNr].toString())
                currentAnswer = checkAnswer(true, questionList, questionNr)
                questionNr++
                if (questionNr < numberOfQuestions){
                    questionTextView.text = showNextQuestion(questionNr, questionList)
                    showQuestionNrTextView.text = "Question ${questionNr + 1}/${numberOfQuestions}"
                }else{
                    questionTextView.text ="Completed!"
                    btnTrue.visibility = View.GONE
                    btnFalse.visibility = View.GONE
                    btnGoBack.visibility = View.VISIBLE
                }
                if(currentAnswer){
                    score++
                }
                showScore.text = "Score: $score"
            }
        }

        btnFalse.setOnClickListener{
            if(ifGameInProgress(questionNr, numberOfQuestions)) {
                Log.d("TAG", questionList.toString())
                currentAnswer = checkAnswer(false, questionList, questionNr)
                questionNr++
                if (questionNr < numberOfQuestions){
                    questionTextView.text = showNextQuestion(questionNr, questionList)
                    showQuestionNrTextView.text = "Question ${questionNr + 1}/${numberOfQuestions}"
                }else{
                    questionTextView.text ="Completed!"
                    btnTrue.visibility = View.GONE
                    btnFalse.visibility = View.GONE
                    btnGoBack.visibility = View.VISIBLE
                }
                if(currentAnswer){
                    score++
                }
                showScore.text = "Score: $score"
            }
        }

        btnGoBack.setOnClickListener{
            goToMainFragment()
            btnGoBack.visibility = View.GONE
            btnTrue.visibility = View.VISIBLE
            btnFalse.visibility = View.VISIBLE
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            StandardQuizFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private fun proceedToNextQuestion(view: TextView){

    }
    private fun checkAnswer(selectedAnswer: Boolean, questionList: List<TFQuestion>, questionIndex: Int): Boolean{
        val correctAnswer = questionList[questionIndex].correctAnswer
        Log.d("TAG", "$correctAnswer == $selectedAnswer")
        return if (selectedAnswer == correctAnswer) {
            // Perform action for correct answer
            Toast.makeText(requireContext(), "Correct!", Toast.LENGTH_SHORT).show()
            true
        } else {
            // Perform action for wrong answer
            Toast.makeText(requireContext(), "Wrong!", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun getRandomQuestionIndexes(maxNumber: Int, nrOfQuestions: Int): List<Int> {
        val random = RandomIndexes()
        val randomNumbers = mutableListOf<Int>()
        var i = 0

        while (i < nrOfQuestions) {
            val randomNumber = random.nextInt(maxNumber)
            if (!randomNumbers.contains(randomNumber)) {
                randomNumbers.add(randomNumber)
                i++
            }
        }

        return randomNumbers
    }
    private fun showNextQuestion(index: Int, questionList: List<TFQuestion>): String{
        return questionList[index].question
    }

    private fun ifGameInProgress(counter: Int, maxNumber: Int): Boolean{
        if(counter < maxNumber){
            return true
        }
        return false
    }

    private fun getQuestionList(numberOfQuestions: Int, category: String, language: String): List<TFQuestion>{
        val csvReader = CSVReader()
        val wholeQuestionList = csvReader.readCSVData(requireContext(), category, language)
        val sessionQuestionList = mutableListOf<TFQuestion>()

        val indexes = getRandomQuestionIndexes(wholeQuestionList.size, numberOfQuestions)
        var questionIndex: Int = 0
        for (index in indexes){
            sessionQuestionList.add(questionIndex, wholeQuestionList[index])
            questionIndex++
        }

        return sessionQuestionList
    }
    private fun getDataFromSharedPreferences(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    private fun setLanguagePreferencesToView(language: String, view: View){
        val btnTrue = view.findViewById<Button>(R.id.btnTrue)
        val btnFalse = view.findViewById<Button>(R.id.btnFalse)
        val btnGoBack = view.findViewById<Button>(R.id.btnGoBack)

        when(language){
            "en"->{
                btnTrue.text = "True"
                btnFalse.text = "False"
                btnGoBack.text = "Go Back"
            }
            "ro"->{
                btnTrue.text = "Adevarat"
                btnFalse.text = "Fals"
                btnGoBack.text = "Inapoi"
            }
        }
    }

    private fun goToMainFragment(){
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }
}