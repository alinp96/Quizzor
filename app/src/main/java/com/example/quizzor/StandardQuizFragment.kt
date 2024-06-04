package com.example.quizzor

import android.animation.ObjectAnimator
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
import android.view.animation.LinearInterpolator
import android.widget.*
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.auth.User

class StandardQuizFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var progressBarTimer: ProgressBar
    private lateinit var questionList: List<TFQuestion>
    private lateinit var view: View
    private lateinit var questionTextView: TextView
    private lateinit var showQuestionNrTextView: TextView
    private lateinit var showScore: TextView
    private lateinit var btnTrue: ImageButton
    private lateinit var btnFalse: ImageButton
    private lateinit var btnGoBack: ImageButton

    private lateinit var backButton: Button
    private lateinit var titleTextView: TextView

    private lateinit var dot: Array<ImageView>
    private lateinit var dotQ1: ImageView
    private lateinit var dotQ2: ImageView
    private lateinit var dotQ3: ImageView
    private lateinit var dotQ4: ImageView
    private lateinit var dotQ5: ImageView
    private lateinit var dotQ6: ImageView
    private lateinit var dotQ7: ImageView
    private lateinit var dotQ8: ImageView
    private lateinit var dotQ9: ImageView
    private lateinit var dotQ10: ImageView

    private var currentAnswer: Boolean = false
    private var score: Int = 0
    private var questionNr: Int = 0
    private var numberOfQuestions: Int = 10

    private var documentName: String = ""
    private lateinit var userManager: UserManagement

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

        val activity = requireActivity()
        activity.title = "$category questions"

        val mActivity = activity as? MainActivity
        userManager = mActivity?.getUserManagement() ?: throw IllegalStateException("MainActivity expected")

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_standard_quiz, container, false)
        questionTextView = view.findViewById<TextView>(R.id.textViewQuestion)
        showQuestionNrTextView = view.findViewById<TextView>(R.id.textViewShowQuestion)
        showScore = view.findViewById<TextView>(R.id.textViewShowScore)
        btnTrue = view.findViewById<ImageButton>(R.id.btnTrue)
        btnFalse = view.findViewById<ImageButton>(R.id.btnFalse)
        btnGoBack = view.findViewById<ImageButton>(R.id.btnGoBack)
        progressBarTimer = view.findViewById<ProgressBar>(R.id.progressBarTimer)

        backButton = view.findViewById<Button>(R.id.backButton)
        backButton.visibility = View.GONE
        titleTextView = view.findViewById<TextView>(R.id.titleTextView)

        dotQ1 = view.findViewById<ImageView>(R.id.dotQ1)
        dotQ2 = view.findViewById<ImageView>(R.id.dotQ2)
        dotQ3 = view.findViewById<ImageView>(R.id.dotQ3)
        dotQ4 = view.findViewById<ImageView>(R.id.dotQ4)
        dotQ5 = view.findViewById<ImageView>(R.id.dotQ5)
        dotQ6 = view.findViewById<ImageView>(R.id.dotQ6)
        dotQ7 = view.findViewById<ImageView>(R.id.dotQ7)
        dotQ8 = view.findViewById<ImageView>(R.id.dotQ8)
        dotQ9 = view.findViewById<ImageView>(R.id.dotQ9)
        dotQ10 = view.findViewById<ImageView>(R.id.dotQ10)

        dot = Array(10) { ImageView(requireContext())}
        dot[0] = dotQ1
        dot[1] = dotQ2
        dot[2] = dotQ3
        dot[3] = dotQ4
        dot[4] = dotQ5
        dot[5] = dotQ6
        dot[6] = dotQ7
        dot[7] = dotQ8
        dot[8] = dotQ9
        dot[9] = dotQ10

        hideBottomNavigation()
        // Reset
        score = 0
        questionNr = 0
        numberOfQuestions = 10

        setLanguagePreferencesToView(language, view)

        // Load 10 random questions from the selected category and language
        //questionList = getQuestionList(numberOfQuestions, category, language)
        questionList = emptyList()


        // change the Category text
        titleTextView.text = "$category Questions"

        backButton.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
        documentName = "${category.replace(" ", "").lowercase()}_${language}"
        // Start the quiz
        //proceedToNextQuestion(questionTextView, questionNr, numberOfQuestions, score)

        fetchRandomQuestions("questions", documentName, 10)
            .addOnSuccessListener { questions ->
                // Use the list of TFQuestion objects
                questionList = questions
                // Start the quiz
                proceedToNextQuestion(questionTextView, questionNr, numberOfQuestions, score)
            }
            .addOnFailureListener { exception ->
                // Handle the error
                println("Error fetching questions: ${exception.message}")
            }

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
            showBottomNavigation()
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

    private fun fetchRandomQuestions(collectionName: String, documentName: String, numberOfQuestions: Int): Task<List<TFQuestion>> {
        val db = FirebaseFirestore.getInstance()

        // Task to handle the asynchronous Firestore call
        Log.d("TAG" ,documentName)
        val task = db.collection(collectionName).document(documentName).get()
            .continueWith { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.exists()) {
                        // Parse the document data to extract questions
                        val questionsMap = document.data as Map<String, Map<String, Any>>

                        // Convert the map to a list of TFQuestion objects
                        val questionList = questionsMap.values.map {
                            TFQuestion(
                                question = it["question"] as String,
                                correctAnswer = it["correctAnswer"] as Boolean
                            )
                        }.toMutableList() // Convert to a mutable list

                        // Shuffle the list to randomize the questions
                        questionList.shuffle()

                        // Select the first 'numberOfQuestions' questions from the shuffled list
                        questionList.take(numberOfQuestions)
                    } else {
                        emptyList<TFQuestion>()
                    }
                } else {
                    throw task.exception ?: Exception("Unknown error occurred")
                }
            }

        return task
    }

    private fun proceedToNextQuestion(view: TextView, questionNr: Int, numberOfQuestions: Int, score: Int){
        questionTextView.text =  showNextQuestion(questionNr, questionList)
        showQuestionNrTextView.text = "${questionNr + 1}/${numberOfQuestions}"
        showScore.text = "$score"
        btnTrue.visibility = View.VISIBLE
        btnFalse.visibility = View.VISIBLE
        progressBarTimer.visibility = View.VISIBLE
        startTimer()
    }

    private fun proceedToEndGame(){
        // documentName = movies_en, booskandliterature_en
        val db = FirebaseFirestore.getInstance()
        val docId = userManager.getLoggedInUserId().toString()
        val docRef = db.collection("users").document(docId)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val currentScoreMap = documentSnapshot.get("score") as? Map<String, Int> ?: emptyMap()
            val previousScore = (currentScoreMap["${documentName.toString().dropLast(3)}"] as? Number)?.toInt() ?: 0
            var updatedScore = 0
            val updatedScoreMap = currentScoreMap.toMutableMap()
            //val updatedScoreValue = updatedScoreMap["${documentName.toString().dropLast(3)}"]
            updatedScore = previousScore + score
            updatedScoreMap["${documentName.toString().dropLast(3)}"] = updatedScore
            docRef.update("score", updatedScoreMap)
            questionTextView.text ="Completed!\n Round points: ${score} \n Total category points: ${updatedScore}"
        }

        btnTrue.visibility = View.GONE
        btnFalse.visibility = View.GONE
        progressBarTimer.visibility = View.GONE
        btnGoBack.visibility = View.VISIBLE
        stopTimer()
    }

    private fun updateScoreIfNeeded(score: Int){
        showScore.text = "$score"
    }
    private fun checkAnswer(selectedAnswer: Boolean, questionList: List<TFQuestion>, questionIndex: Int, timeLimitExceeded: Boolean): Boolean{
        val correctAnswer = questionList[questionIndex].correctAnswer
        var returnVal = false

        returnVal = if (timeLimitExceeded){
            //Toast.makeText(requireContext(), "Time exceeded!", Toast.LENGTH_SHORT).show()
            dotLogic(questionIndex, false)
            false
        } else if (selectedAnswer == correctAnswer) {
            // Perform action for correct answer
            //Toast.makeText(requireContext(), "Correct!", Toast.LENGTH_SHORT).show()
            dotLogic(questionIndex, true)
            true
        } else {
            // Perform action for wrong answer
            //Toast.makeText(requireContext(), "Wrong!", Toast.LENGTH_SHORT).show()
            dotLogic(questionIndex, false)
            false
        }
        return returnVal
    }

    private fun dotLogic(idNr: Int, isCorrectAnswer: Boolean) {
        if (isCorrectAnswer) {
            dot[idNr].setImageResource(R.drawable.ic_greendot)
        } else {
            dot[idNr].setImageResource(R.drawable.ic_reddot)
        }
        dot[idNr].visibility = View.VISIBLE
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
        // Set the initial progress to maximum
        progressBarTimer.progress = progressBarTimer.max

        countDownTimer = object : CountDownTimer(7000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Calculate the progress to reach at this tick
                val targetProgress = (millisUntilFinished * progressBarTimer.max / 7000).toInt()

                val animatedProgress = ObjectAnimator.ofInt(progressBarTimer, "progress", progressBarTimer.progress, targetProgress)
                animatedProgress.duration = 1000 // Animation duration in milliseconds (1 second)
                animatedProgress.interpolator = LinearInterpolator() // Linear interpolator for smoother animation
                animatedProgress.addUpdateListener {
                    progressBarTimer.progress = it.animatedValue as Int
                }
                animatedProgress.start()
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
        if(currentAnswer){
            score++
        }else{
            score--
        }
        if (questionNr < numberOfQuestions){
            proceedToNextQuestion(questionTextView, questionNr, numberOfQuestions, score)
        }else{
            proceedToEndGame()
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
        //val btnTrue = view.findViewById<Button>(R.id.btnTrue)
        //val btnFalse = view.findViewById<Button>(R.id.btnFalse)
        val btnGoBack = view.findViewById<ImageButton>(R.id.btnGoBack)

        when(language){
            "en"->{
                //btnTrue.text = "True"
                //btnFalse.text = "False"
                //btnGoBack.text = "Go Back"
            }
            "ro"->{
                //btnTrue.text = "Adevarat"
                //btnFalse.text = "Fals"
                //btnGoBack.text = "Inapoi"
            }
        }
    }

    private fun goToMainFragment(){
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun Fragment.hideBottomNavigation() {
        (activity as? MainActivity)?.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.visibility = View.GONE
    }

    fun Fragment.showBottomNavigation() {
        (activity as? MainActivity)?.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.visibility = View.VISIBLE
    }
}