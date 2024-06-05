package com.example.quizzor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class LeaderboardFragment : Fragment() {
    private val scores = mapOf(
        "beverages" to "Beverages",
        "biology" to "Biology",
        "booksandliterature" to "Books and Literature",
        "celebrityandpopculture" to "Celebrity and Pop Culture",
        "cheistry" to "Chemistry",
        "computerscience" to "Computer Science",
        "countriesandcapitals" to "Countries and Capitals",
        "cuisine" to "Cuisine",
        "famoushistoricalfigures" to "Famous Historical Figures",
        "famouspersonalities" to "Famous Personalities",
        "foodandtrivia" to "Food and Trivia",
        "geographicfactsandtrivia" to "Geographic Facts and Trivia",
        "historicalevents" to "Historical Events",
        "ingredients" to "Ingredients",
        "memorablesportingevents" to "Memorable Sporting Events",
        "movies" to "Movies",
        "music" to "Music",
        "physics" to "Physics",
        "soccer" to "Soccer",
        "trivia" to "Trivia",
        "tvshows" to "TV Shows",
        "unusualsportsfacts" to "Unusual Sports Facts",
        "videogames" to "Video Games",
        "worldfacts" to "World Facts",
        "total" to "Total"
    )

    private lateinit var tvTitle: TextView
    private lateinit var userManager: UserManagement
    private lateinit var firestore: FirebaseFirestore
    private lateinit var llLeaderboardButtons: LinearLayout
    private lateinit var btnAllScores: Button
    private lateinit var btnLeaderboard: Button
    private lateinit var llLeaderboardContent: LinearLayout
    private lateinit var backButton: Button
    private lateinit var scoresTable: TableLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_leaderboard, container, false)
        val mActivity = activity as? MainActivity
        userManager = mActivity?.getUserManagement() ?: throw IllegalStateException("MainActivity expected")
        scoresTable = view.findViewById<TableLayout>(R.id.scores_table)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        tvTitle = view.findViewById<TextView>(R.id.titleTextView)
        llLeaderboardButtons = view.findViewById<LinearLayout>(R.id.llLeaderboardButtons)
        btnAllScores = view.findViewById<Button>(R.id.btnAllScores)
        btnLeaderboard = view.findViewById<Button>(R.id.btnLeaderboard)
        llLeaderboardContent = view.findViewById<LinearLayout>(R.id.llLeaderboardContent)
        backButton = view.findViewById<Button>(R.id.backButton)

        tvTitle.text = "Leaderboard"


        btnAllScores.setOnClickListener{
            showScoresView(view)
            tvTitle.text = "Your Scores"
        }

        btnLeaderboard.setOnClickListener{
            showTopUsers()
            tvTitle.text = "Top 10 Players"
        }

        backButton.setOnClickListener{
            showLeaderboardMainView()
            tvTitle.text = "Leaderboard"
        }

        return view
    }

    private fun showScoresView(view: View){
        llLeaderboardButtons.visibility = View.GONE
        llLeaderboardContent.visibility = View.VISIBLE
        showScores(view)
    }

    private fun showTopUsers(){
        llLeaderboardButtons.visibility = View.GONE
        llLeaderboardContent.visibility = View.VISIBLE
        fetchTop10Users()
    }

    private fun showLeaderboardMainView(){
        llLeaderboardButtons.visibility = View.VISIBLE
        llLeaderboardContent.visibility = View.GONE
    }

    private fun showScores(view: View){
        scoresTable = view.findViewById<TableLayout>(R.id.scores_table)

        val db = FirebaseFirestore.getInstance()
        val docId = userManager.getLoggedInUserId().toString()
        val docRef = db.collection("users").document(docId)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val currentScoreMap = documentSnapshot.get("score") as? Map<String, Int> ?: emptyMap()

            // Iterate over the scores map and create a TableRow for each category
            for ((category, categoryPretty) in scores) {
                val tableRow = TableRow(context).apply {
                    layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )
                }

                // Create a TextView for the category name
                val categoryTextView = TextView(context).apply {
                    text = categoryPretty
                    textSize = 16f
                    setPadding(8, 8, 8, 8)
                }
                tableRow.addView(categoryTextView)

                // Create a TextView for the score
                val scoreTextView = TextView(context).apply {
                    val actualScore = (currentScoreMap[category] as? Number)?.toInt() ?: 0
                    text = actualScore.toString()
                    textSize = 16f
                    setPadding(8, 8, 8, 8)
                }
                tableRow.addView(scoreTextView)

                // Add the TableRow to the TableLayout
                scoresTable.addView(tableRow)
            }
        }
    }

    private fun fetchTop10Users() {
        clearTable()
        firestore.collection("users")
            .orderBy("score.total", Query.Direction.DESCENDING)
            .limit(10)
            .get()
            .addOnSuccessListener { result ->
                // Add a header row for the table
                val headerRow = TableRow(context).apply {
                    layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )
                }

                val rankHeader = TextView(context).apply {
                    text = "Rank"
                    textSize = 18f
                    setPadding(8, 8, 8, 8)
                }
                headerRow.addView(rankHeader)

                val nicknameHeader = TextView(context).apply {
                    text = "Nickname"
                    textSize = 18f
                    setPadding(8, 8, 8, 8)
                }
                headerRow.addView(nicknameHeader)

                val scoreHeader = TextView(context).apply {
                    text = "Total Score"
                    textSize = 18f
                    setPadding(8, 8, 8, 8)
                }
                headerRow.addView(scoreHeader)

                scoresTable.addView(headerRow)

                // Iterate over the result and create a TableRow for each user
                var rank = 1
                for (document in result) {
                    val nickname = document.getString("nickname") ?: "Unknown"
                    val totalScore = document.get("score.total")?.toString() ?: "0"

                    val tableRow = TableRow(context).apply {
                        layoutParams = TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT
                        )
                    }

                    // Create a TextView for the rank
                    val rankTextView = TextView(context).apply {
                        text = rank.toString()
                        textSize = 16f
                        setPadding(8, 8, 8, 8)
                    }
                    tableRow.addView(rankTextView)

                    // Create a TextView for the nickname
                    val nicknameTextView = TextView(context).apply {
                        text = nickname
                        textSize = 16f
                        setPadding(8, 8, 8, 8)
                    }
                    tableRow.addView(nicknameTextView)

                    // Create a TextView for the total score
                    val scoreTextView = TextView(context).apply {
                        text = totalScore
                        textSize = 16f
                        setPadding(8, 8, 8, 8)
                    }
                    tableRow.addView(scoreTextView)

                    // Add the TableRow to the TableLayout
                    scoresTable.addView(tableRow)

                    rank++
                }
            }
            .addOnFailureListener { exception ->
                Log.w("LeaderboardFragment", "Error getting documents: ", exception)
            }
    }

    private fun clearTable() {
        scoresTable.removeAllViews()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LeaderboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            LeaderboardFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}