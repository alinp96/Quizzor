package com.example.quizzor

import android.content.Context
import android.content.SharedPreferences
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
    private val scoresEn = mapOf(
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

    private val scoresRo = mapOf(
        "beverages" to "Băuturi",
        "biology" to "Biologie",
        "booksandliterature" to "Cărți și Literatură",
        "celebrityandpopculture" to "Celebrități și Cultură Pop",
        "cheistry" to "Chimie",
        "computerscience" to "Informatică",
        "countriesandcapitals" to "Țări și Capitale",
        "cuisine" to "Bucătărie",
        "famoushistoricalfigures" to "Figuri Istorice Celebre",
        "famouspersonalities" to "Personalități Celebre",
        "foodandtrivia" to "Alimente și Trivialități",
        "geographicfactsandtrivia" to "Fapte și Trivialități Geografice",
        "historicalevents" to "Evenimente Istorice",
        "ingredients" to "Ingrediente",
        "memorablesportingevents" to "Evenimente Sportive Memorabile",
        "movies" to "Filme",
        "music" to "Muzică",
        "physics" to "Fizică",
        "soccer" to "Fotbal",
        "trivia" to "Trivialități",
        "tvshows" to "Emisiuni TV",
        "unusualsportsfacts" to "Fapte Neobișnuite Despre Sport",
        "videogames" to "Jocuri Video",
        "worldfacts" to "Fapte Despre Lume",
        "total" to "Total"
    )

    private val scoresDe = mapOf(
        "beverages" to "Getränke",
        "biology" to "Biologie",
        "booksandliterature" to "Bücher und Literatur",
        "celebrityandpopculture" to "Prominente und Popkultur",
        "cheistry" to "Chemie",
        "computerscience" to "Informatik",
        "countriesandcapitals" to "Länder und Hauptstädte",
        "cuisine" to "Küche",
        "famoushistoricalfigures" to "Berühmte historische Persönlichkeiten",
        "famouspersonalities" to "Berühmte Persönlichkeiten",
        "foodandtrivia" to "Essen und Wissenswertes",
        "geographicfactsandtrivia" to "Geografische Fakten und Wissenswertes",
        "historicalevents" to "Historische Ereignisse",
        "ingredients" to "Zutaten",
        "memorablesportingevents" to "Denkwürdige Sportereignisse",
        "movies" to "Filme",
        "music" to "Musik",
        "physics" to "Physik",
        "soccer" to "Fußball",
        "trivia" to "Wissenswertes",
        "tvshows" to "Fernsehshows",
        "unusualsportsfacts" to "Ungewöhnliche Sportfakten",
        "videogames" to "Videospiele",
        "worldfacts" to "Weltfakten",
        "total" to "Gesamt"
    )

    private val scoresFr = mapOf(
        "beverages" to "Boissons",
        "biology" to "Biologie",
        "booksandliterature" to "Livres et Littérature",
        "celebrityandpopculture" to "Célébrités et Culture Pop",
        "cheistry" to "Chimie",
        "computerscience" to "Informatique",
        "countriesandcapitals" to "Pays et Capitales",
        "cuisine" to "Cuisine",
        "famoushistoricalfigures" to "Personnages Historiques Célèbres",
        "famouspersonalities" to "Personnalités Célèbres",
        "foodandtrivia" to "Nourriture et Anecdotes",
        "geographicfactsandtrivia" to "Faits Géographiques et Anecdotes",
        "historicalevents" to "Événements Historiques",
        "ingredients" to "Ingrédients",
        "memorablesportingevents" to "Événements Sportifs Mémorables",
        "movies" to "Films",
        "music" to "Musique",
        "physics" to "Physique",
        "soccer" to "Football",
        "trivia" to "Anecdotes",
        "tvshows" to "Émissions de Télévision",
        "unusualsportsfacts" to "Faits Sportifs Inhabituels",
        "videogames" to "Jeux Vidéo",
        "worldfacts" to "Faits Mondiaux",
        "total" to "Total"
    )

    private val scoresHu = mapOf(
        "beverages" to "Italok",
        "biology" to "Biológia",
        "booksandliterature" to "Könyvek és Irodalom",
        "celebrityandpopculture" to "Hírességek és Popkultúra",
        "cheistry" to "Kémia",
        "computerscience" to "Számítástechnika",
        "countriesandcapitals" to "Országok és Fővárosok",
        "cuisine" to "Konyhaművészet",
        "famoushistoricalfigures" to "Híres Történelmi Személyek",
        "famouspersonalities" to "Híres Személyiségek",
        "foodandtrivia" to "Ételek és Érdekességek",
        "geographicfactsandtrivia" to "Földrajzi Tények és Érdekességek",
        "historicalevents" to "Történelmi Események",
        "ingredients" to "Hozzávalók",
        "memorablesportingevents" to "Emlékezetes Sportesemények",
        "movies" to "Filmek",
        "music" to "Zene",
        "physics" to "Fizika",
        "soccer" to "Foci",
        "trivia" to "Érdekességek",
        "tvshows" to "Tévéműsorok",
        "unusualsportsfacts" to "Szokatlan Sporttények",
        "videogames" to "Videójátékok",
        "worldfacts" to "Világtények",
        "total" to "Összesen"
    )

    private val scoresJp = mapOf(
        "beverages" to "飲み物",
        "biology" to "生物学",
        "booksandliterature" to "本と文学",
        "celebrityandpopculture" to "有名人とポップカルチャー",
        "cheistry" to "化学",
        "computerscience" to "コンピュータサイエンス",
        "countriesandcapitals" to "国と首都",
        "cuisine" to "料理",
        "famoushistoricalfigures" to "有名な歴史上の人物",
        "famouspersonalities" to "有名な人物",
        "foodandtrivia" to "食べ物と雑学",
        "geographicfactsandtrivia" to "地理的な事実と雑学",
        "historicalevents" to "歴史的出来事",
        "ingredients" to "材料",
        "memorablesportingevents" to "記憶に残るスポーツイベント",
        "movies" to "映画",
        "music" to "音楽",
        "physics" to "物理学",
        "soccer" to "サッカー",
        "trivia" to "雑学",
        "tvshows" to "テレビ番組",
        "unusualsportsfacts" to "珍しいスポーツの事実",
        "videogames" to "ビデオゲーム",
        "worldfacts" to "世界の事実",
        "total" to "合計"
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
    private lateinit var language: String
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_leaderboard, container, false)
        (activity as MainActivity).switchToGeneralMusic()
        val mActivity = activity as? MainActivity
        userManager = mActivity?.getUserManagement() ?: throw IllegalStateException("MainActivity expected")
        scoresTable = view.findViewById<TableLayout>(R.id.scores_table)
        sharedPreferences = requireActivity().getSharedPreferences("userPreferences", Context.MODE_PRIVATE)
        language = getDataFromSharedPreferences("language")

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        tvTitle = view.findViewById<TextView>(R.id.titleTextView)
        llLeaderboardButtons = view.findViewById<LinearLayout>(R.id.llLeaderboardButtons)
        btnAllScores = view.findViewById<Button>(R.id.btnAllScores)
        btnLeaderboard = view.findViewById<Button>(R.id.btnLeaderboard)
        llLeaderboardContent = view.findViewById<LinearLayout>(R.id.llLeaderboardContent)
        backButton = view.findViewById<Button>(R.id.backButton)

        loadUIText(language)


        btnAllScores.setOnClickListener{
            (activity as MainActivity).playButtonClickSound()
            showScoresView(view)
            changeTitleLanguage(language, "scores")
        }

        btnLeaderboard.setOnClickListener{
            (activity as MainActivity).playButtonClickSound()
            showTopUsers()
            changeTitleLanguage(language, "top")
        }

        backButton.setOnClickListener{
            (activity as MainActivity).playButtonClickSound()
            showLeaderboardMainView()
            changeTitleLanguage(language, "leaderboard")
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
        clearTable()
        scoresTable = view.findViewById<TableLayout>(R.id.scores_table)

        val db = FirebaseFirestore.getInstance()
        val docId = userManager.getLoggedInUserId().toString()
        val docRef = db.collection("users").document(docId)
        val scores = getScores(language)
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

    private fun getDataFromSharedPreferences(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    private fun loadUIText(language: String){
        when (language){
            "en" -> {
                btnAllScores.text = "Show all scores"
                btnLeaderboard.text = "Show leaderboard"
                backButton.text = "Back"
                tvTitle.text = "Leaderboard"
            }
            "ro" -> {
                btnAllScores.text = "Afișează toate scorurile"
                btnLeaderboard.text = "Afișează clasamentul"
                backButton.text = "Înapoi"
                tvTitle.text = "Clasament"
            }
            "de" -> {
                btnAllScores.text = "Alle Punktzahlen anzeigen"
                btnLeaderboard.text = "Bestenliste anzeigen"
                backButton.text = "Zurück"
                tvTitle.text = "Bestenliste"
            }
            "fr" -> {
                btnAllScores.text = "Afficher tous les scores"
                btnLeaderboard.text = "Afficher le classement"
                backButton.text = "Retour"
                tvTitle.text = "Classement"
            }
            "hu" -> {
                btnAllScores.text = "Összes pontszám megtekintése"
                btnLeaderboard.text = "Ranglista megtekintése"
                backButton.text = "Vissza"
                tvTitle.text = "Ranglista"
            }
            "jp" -> {
                btnAllScores.text = "すべてのスコアを表示"
                btnLeaderboard.text = "リーダーボードを表示"
                backButton.text = "戻る"
                tvTitle.text = "リーダーボード"
            }
        }
    }

    private fun changeTitleLanguage(language: String, type: String){
        when (type){
            "leaderboard" -> {
                when(language) {
                    "en" -> {
                        tvTitle.text = "Leaderboard"
                    }
                    "ro" -> {
                        tvTitle.text = "Clasament"
                    }
                    "de" -> {
                        tvTitle.text = "Bestenliste"
                    }
                    "fr" -> {
                        tvTitle.text = "Classement"
                    }
                    "hu" -> {
                        tvTitle.text = "Ranglista"
                    }
                    "jp" -> {
                        tvTitle.text = "リーダーボード"
                    }
                }
            }
            "scores" -> {
                when(language) {
                    "en" -> {
                        tvTitle.text = "Your Scores"
                    }
                    "ro" -> {
                        tvTitle.text = "Scorurile tale"
                    }
                    "de" -> {
                        tvTitle.text = "Deine Punktzahlen"
                    }
                    "fr" -> {
                        tvTitle.text = "Vos scores"
                    }
                    "hu" -> {
                        tvTitle.text = "A te pontszámaid"
                    }
                    "jp" -> {
                        tvTitle.text = "あなたのスコア"
                    }
                }
            }
            "top" -> {
                when(language) {
                    "en" -> {
                        tvTitle.text = "Top 10 Players"
                    }
                    "ro" -> {
                        tvTitle.text = "Top 10 jucători"
                    }
                    "de" -> {
                        tvTitle.text = "Top 10 Spieler"
                    }
                    "fr" -> {
                        tvTitle.text = "Top 10 Joueurs"
                    }
                    "hu" -> {
                        tvTitle.text = "Top 10 játékos"
                    }
                    "jp" -> {
                        tvTitle.text = "トップ10プレイヤー"
                    }
                }
            }
        }
    }

    fun getScores(language: String): Map<String, String> {
        return when (language.toLowerCase()) {
            "en" -> scoresEn
            "ro" -> scoresRo
            "de" -> scoresDe
            "fr" -> scoresFr
            "hu" -> scoresHu
            "jp" -> scoresJp
            else -> scoresEn // Default to English if language not supported
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LeaderboardFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}