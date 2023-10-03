package com.example.quizzor

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

class StandardQuizFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var progressBarTimer: ProgressBar
    private lateinit var questionList: List<TFQuestion>
    private lateinit var view: View
    private lateinit var questionTextView: TextView
    private lateinit var showQuestionNrTextView: TextView
    private lateinit var showScore: TextView
    private lateinit var btnTrue: Button
    private lateinit var btnFalse: Button
    private lateinit var btnGoBack: Button
    private var currentAnswer: Boolean = false
    private var score: Int = 0
    private var questionNr: Int = 0
    private var numberOfQuestions: Int = 10

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
        view = inflater.inflate(R.layout.fragment_standard_quiz, container, false)
        questionTextView = view.findViewById<TextView>(R.id.textViewQuestion)
        showQuestionNrTextView = view.findViewById<TextView>(R.id.textViewShowQuestion)
        showScore = view.findViewById<TextView>(R.id.textViewShowScore)
        btnTrue = view.findViewById<Button>(R.id.btnTrue)
        btnFalse = view.findViewById<Button>(R.id.btnFalse)
        btnGoBack = view.findViewById<Button>(R.id.btnGoBack)
        progressBarTimer = view.findViewById<ProgressBar>(R.id.progressBarTimer)

        // Reset
        score = 0
        questionNr = 0
        numberOfQuestions = 10

        setLanguagePreferencesToView(language, view)

        // Load 10 random questions from the selected category and language
        questionList = getQuestionList(numberOfQuestions, category, language)

        // Start the quiz
        proceedToNextQuestion(questionTextView, questionNr, numberOfQuestions, score)

        btnTrue.setOnClickListener{
            if(ifGameInProgress(questionNr, numberOfQuestions)) {
                answerLogic(selectedAnswer = true, timeLimitExceeded = false)
            }
        }

        btnFalse.setOnClickListener{
            if(ifGameInProgress(questionNr, numberOfQuestions)) {
                answerLogic(selectedAnswer = false, timeLimitExceeded = false)
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

    private fun proceedToNextQuestion(view: TextView, questionNr: Int, numberOfQuestions: Int, score: Int){
        questionTextView.text =  showNextQuestion(questionNr, questionList)
        showQuestionNrTextView.text = "Question ${questionNr + 1}/${numberOfQuestions}"
        showScore.text = "Score: ${score}"
        btnTrue.visibility = View.VISIBLE
        btnFalse.visibility = View.VISIBLE
        progressBarTimer.visibility = View.VISIBLE
        startTimer()
    }

    private fun proceedToEndGame(){
        questionTextView.text ="Completed!"
        btnTrue.visibility = View.GONE
        btnFalse.visibility = View.GONE
        progressBarTimer.visibility = View.GONE
        btnGoBack.visibility = View.VISIBLE
        stopTimer()
    }

    private fun updateScoreIfNeeded(score: Int){
        showScore.text = "Score: ${score}"
    }
    private fun checkAnswer(selectedAnswer: Boolean, questionList: List<TFQuestion>, questionIndex: Int, timeLimitExceeded: Boolean): Boolean{
        val correctAnswer = questionList[questionIndex].correctAnswer
        var returnVal = false

        returnVal = if (timeLimitExceeded){
            Toast.makeText(requireContext(), "Time exceeded!", Toast.LENGTH_SHORT).show()
            false
        } else if (selectedAnswer == correctAnswer) {
            // Perform action for correct answer
            Toast.makeText(requireContext(), "Correct!", Toast.LENGTH_SHORT).show()
            true
        } else {
            // Perform action for wrong answer
            Toast.makeText(requireContext(), "Wrong!", Toast.LENGTH_SHORT).show()
            false
        }
        return returnVal
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

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(7000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                progressBarTimer.progress = millisUntilFinished.toInt()
            }

            override fun onFinish() {
                // Time's up, mark the question as wrong
                answerLogic(selectedAnswer = false, timeLimitExceeded = true)
            }
        }
        countDownTimer.start()
    }

    private fun stopTimer() {
        countDownTimer.cancel()
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

    private fun answerLogic(selectedAnswer: Boolean, timeLimitExceeded: Boolean){
        stopTimer()
        currentAnswer = checkAnswer(selectedAnswer, questionList, questionNr, timeLimitExceeded)
        questionNr++
        if (questionNr < numberOfQuestions){
            proceedToNextQuestion(questionTextView, questionNr, numberOfQuestions, score)
        }else{
            proceedToEndGame()
        }
        if(currentAnswer){
            score++
        }
        updateScoreIfNeeded(score)
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