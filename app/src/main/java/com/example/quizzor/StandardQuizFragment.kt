package com.example.quizzor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class StandardQuizFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_standard_quiz, container, false)
        val questionTextView = view.findViewById<TextView>(R.id.textViewQuestion)
        val showQuestionNrTextView = view.findViewById<TextView>(R.id.textViewShowQuestion)
        val showScore = view.findViewById<TextView>(R.id.textViewShowScore)

        val btnTrue = view.findViewById<Button>(R.id.btnTrue)
        val btnFalse = view.findViewById<Button>(R.id.btnFalse)
        var score: Int = 0
        var questionNr: Int = 0
        var numberOfQuestions: Int = 10
        var currentAnswer: Boolean = false

        val questionList: List<TFQuestion> = getQuestionList(numberOfQuestions)

        if(ifGameInProgress(questionNr, numberOfQuestions)){
            questionTextView.text =  showNextQuestion(questionNr, questionList)
            showQuestionNrTextView.text = "Question ${questionNr + 1}/${numberOfQuestions}"
            showScore.text = "Score: ${score}"
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
                }
                if(currentAnswer){
                    score++
                }
                showScore.text = "Score: ${score}"
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
                }
                if(currentAnswer){
                    score++
                }
                showScore.text = "Score: ${score}"
            }
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
        Log.d("TAG", "${correctAnswer} == ${selectedAnswer}")
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
        val random = RandomQuestionIndexes()
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

    private fun getQuestionList(numberOfQuestions: Int): List<TFQuestion>{
        val csvReader = CSVReader()
        val wholeQuestionList = csvReader.readCSVData(requireContext())
        val sessionQuestionList = mutableListOf<TFQuestion>()

        val indexes = getRandomQuestionIndexes(wholeQuestionList.size, numberOfQuestions)
        var questionIndex: Int = 0
        for (index in indexes){
            sessionQuestionList.add(questionIndex, wholeQuestionList[index])
            questionIndex++
        }

        return sessionQuestionList
    }
}