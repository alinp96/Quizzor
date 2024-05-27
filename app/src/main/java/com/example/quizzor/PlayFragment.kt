package com.example.quizzor

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayFragment : Fragment() {
    private lateinit var btnBackToMainScreen: ImageButton
    private lateinit var llBackCategory: LinearLayout
    private lateinit var txtViewSubCategories: TextView
    private lateinit var txtChosenCategory: TextView
    private lateinit var ll1stRowOfCategories: View

    private lateinit var ll2ndRowOfCategories: View
    private lateinit var llGeneralKnowledgeSubs: View
    private lateinit var ll1stRowOfScienceAndTechnologySubs: View
    private lateinit var ll2ndRowOfScienceAndTechnologySubs: View
    private lateinit var ll1stRowOfHistoryAndGeographySubs: View
    private lateinit var ll2ndRowOfHistoryAndGeographySubs: View
    private lateinit var ll1stRowOfEntertainmentSubs: View
    private lateinit var ll2ndRowOfEntertainmentSubs: View
    private lateinit var llRowOfSportsSubs: View
    private lateinit var ll1stRowOfFoodAndDrinksSubs: View
    private lateinit var ll2ndRowOfFoodAndDrinksSubs: View

    private lateinit var cvGeneralKnowledge: CardView
    private lateinit var cvScienceAndTechnology: CardView
    private lateinit var cvHistoryAndGeography: CardView
    private lateinit var cvEntertainment: CardView
    private lateinit var cvSports: CardView
    private lateinit var cvFoodAndDrinks: CardView

    private lateinit var tvGeneralKnowledge: TextView
    private lateinit var tvScienceAndTechnology: TextView
    private lateinit var tvHistoryAndGeography: TextView
    private lateinit var tvEntertainment: TextView
    private lateinit var tvSports: TextView
    private lateinit var tvFoodAndDrinks: TextView

    private lateinit var btnBackCategory: ImageButton
    private lateinit var btnStartGame: ImageButton
    private lateinit var btnGoToChooseSubCategory: ImageButton
    private lateinit var cvTrivia: CardView
    private lateinit var cvWorldFacts: CardView
    private lateinit var cvFamousPersonalities: CardView
    private lateinit var cvPhysics: CardView
    private lateinit var cvChemistry: CardView
    private lateinit var cvBiology: CardView
    private lateinit var cvComputerScience: CardView
    private lateinit var cvCountriesAndCapitals: CardView
    private lateinit var cvGeographicFactsAndTrivia: CardView
    private lateinit var cvFamousHistoricalFigures: CardView
    private lateinit var cvHistoricalEvents: CardView
    private lateinit var cvMovies: CardView
    private lateinit var cvTvShows: CardView
    private lateinit var cvMusic: CardView
    private lateinit var cvBooksAndLiterature: CardView
    private lateinit var cvCelebrityAndPopCulture: CardView
    private lateinit var cvVideoGames: CardView
    private lateinit var cvSoccer: CardView
    private lateinit var cvUnusualSportsFacts: CardView
    private lateinit var cvMemorableSportingEvents: CardView
    private lateinit var cvCuisine: CardView
    private lateinit var cvIngredients: CardView
    private lateinit var cvBeverages: CardView
    private lateinit var cvFoodAndTrivia: CardView

    private lateinit var backButton: Button
    private lateinit var tvTitle: TextView

    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Set the title for the ActionBar (or Toolbar)
        val activity = requireActivity()
        activity.title = "Chose your category!"

        sharedPreferences = requireActivity().getSharedPreferences("userPreferences", Context.MODE_PRIVATE)

        val language = getDataFromSharedPreferences("language")

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_play, container, false)
        backButton = view.findViewById<Button>(R.id.backButton)
        tvTitle = view.findViewById<TextView>(R.id.titleTextView)
        btnBackToMainScreen = view.findViewById<ImageButton>(R.id.btnBackToMainScreen)
        llBackCategory = view.findViewById<LinearLayout>(R.id.llBackCategory)
        txtViewSubCategories = view.findViewById<TextView>(R.id.textViewChosenSubCategory)
        txtChosenCategory = view.findViewById<TextView>(R.id.textViewChosenCategory)
        ll1stRowOfCategories = view.findViewById<LinearLayout>(R.id.ll1stRowOfCategoriesButtons)
        ll2ndRowOfCategories = view.findViewById<LinearLayout>(R.id.ll2ndRowOfCategoriesButtons)
        llGeneralKnowledgeSubs = view.findViewById<LinearLayout>(R.id.llRowOfGeneralKnowledgeSubs)
        ll1stRowOfScienceAndTechnologySubs = view.findViewById<LinearLayout>(R.id.ll1stRowOfScienceAndTechnologySubs)
        ll2ndRowOfScienceAndTechnologySubs = view.findViewById<LinearLayout>(R.id.ll2ndRowOfScienceAndTechnologySubs)
        ll1stRowOfHistoryAndGeographySubs = view.findViewById<LinearLayout>(R.id.ll1stRowOfHistoryAndGeographySubs)
        ll2ndRowOfHistoryAndGeographySubs = view.findViewById<LinearLayout>(R.id.ll2ndRowOfHistoryAndGeographySubs)
        ll1stRowOfEntertainmentSubs = view.findViewById<LinearLayout>(R.id.ll1stRowOfEntertainmentSubs)
        ll2ndRowOfEntertainmentSubs = view.findViewById<LinearLayout>(R.id.ll2ndRowOfEntertainmentSubs)
        llRowOfSportsSubs = view.findViewById<LinearLayout>(R.id.llRowOfSportsSubs)
        ll1stRowOfFoodAndDrinksSubs = view.findViewById<LinearLayout>(R.id.ll1stRowOfFoodAndDrinksSubs)
        ll2ndRowOfFoodAndDrinksSubs = view.findViewById<LinearLayout>(R.id.ll2ndRowOfFoodAndDrinksSubs)

        btnGoToChooseSubCategory = view.findViewById<ImageButton>(R.id.btnGoToChooseSubCategory)
        btnBackCategory = view.findViewById<ImageButton>(R.id.btnBackCategory)
        btnStartGame = view.findViewById<ImageButton>(R.id.btnStartGame)
        cvGeneralKnowledge = view.findViewById<CardView>(R.id.cv_generalKnowledge)
        cvTrivia = view.findViewById<CardView>(R.id.cv_trivia)
        cvWorldFacts = view.findViewById<CardView>(R.id.cv_worldFacts)
        cvFamousPersonalities = view.findViewById<CardView>(R.id.cv_famousCelebrities)
        cvScienceAndTechnology = view.findViewById<CardView>(R.id.cv_scienceAndTechnology)
        cvPhysics = view.findViewById<CardView>(R.id.cv_physics)
        cvChemistry = view.findViewById<CardView>(R.id.cv_chemistry)
        cvBiology = view.findViewById<CardView>(R.id.cv_biology)
        cvComputerScience = view.findViewById<CardView>(R.id.cv_computerScience)
        cvHistoryAndGeography = view.findViewById<CardView>(R.id.cv_historyAndGeography)
        cvEntertainment = view.findViewById<CardView>(R.id.cv_entertainment)
        cvSports = view.findViewById<CardView>(R.id.cv_sports)
        cvFoodAndDrinks = view.findViewById<CardView>(R.id.cv_foodAndDrinks)
        cvCountriesAndCapitals = view.findViewById<CardView>(R.id.cv_countriesAndCapitals)
        cvGeographicFactsAndTrivia = view.findViewById<CardView>(R.id.cv_geographicFactsAndTrivia)
        cvFamousHistoricalFigures = view.findViewById<CardView>(R.id.cv_famousHistoricalFigures)
        cvHistoricalEvents = view.findViewById<CardView>(R.id.cv_historicalEvents)
        cvMovies = view.findViewById<CardView>(R.id.cv_movies)
        cvTvShows = view.findViewById<CardView>(R.id.cv_tvShows)
        cvMusic = view.findViewById<CardView>(R.id.cv_music)
        cvBooksAndLiterature = view.findViewById<CardView>(R.id.cv_booksAndLiterature)
        cvCelebrityAndPopCulture = view.findViewById<CardView>(R.id.cv_celebrityAndPopCulture)
        cvVideoGames = view.findViewById<CardView>(R.id.cv_videoGames)
        cvSoccer = view.findViewById<CardView>(R.id.cv_soccer)
        cvUnusualSportsFacts = view.findViewById<CardView>(R.id.cv_unusualSportsFacts)
        cvMemorableSportingEvents = view.findViewById<CardView>(R.id.cv_memorableSportingEvents)
        cvCuisine = view.findViewById<CardView>(R.id.cv_cuisine)
        cvIngredients = view.findViewById<CardView>(R.id.cv_ingredients)
        cvBeverages = view.findViewById<CardView>(R.id.cv_beverages)
        cvFoodAndTrivia = view.findViewById<CardView>(R.id.cv_foodTrivia)

        tvGeneralKnowledge = view.findViewById<TextView>(R.id.tv_generalKnowledge)
        tvScienceAndTechnology = view.findViewById<TextView>(R.id.tv_scienceAndTechnology)
        tvHistoryAndGeography = view.findViewById<TextView>(R.id.tv_historyAndGeography)
        tvEntertainment = view.findViewById<TextView>(R.id.tv_entertainment)
        tvSports = view.findViewById<TextView>(R.id.tv_sports)
        tvFoodAndDrinks = view.findViewById<TextView>(R.id.tv_foodAndDrinks)

        setLanguagePreferencesToView(language)


        // Show categories
        hideEverything()
        showCategories()

        btnBackToMainScreen.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }

        cvGeneralKnowledge.setOnClickListener{
            chosenCategory("General Knowledge")
        }

        cvTrivia.setOnClickListener{
            chosenSubCategory("Trivia")
        }

        cvWorldFacts.setOnClickListener{
            chosenSubCategory("World Facts")
        }

        cvFamousPersonalities.setOnClickListener{
            chosenSubCategory("Famous Personalities")
        }

        cvScienceAndTechnology.setOnClickListener{
            chosenCategory("Science and Technology")
        }

        cvPhysics.setOnClickListener{
            chosenSubCategory("Physics")
        }

        cvChemistry.setOnClickListener{
            chosenSubCategory("Chemistry")
        }

        cvBiology.setOnClickListener{
            chosenSubCategory("Biology")
        }

        cvComputerScience.setOnClickListener{
            chosenSubCategory("Computer Science")
        }

        cvHistoryAndGeography.setOnClickListener{
            chosenCategory("History and Geography")
        }

        cvCountriesAndCapitals.setOnClickListener{
            chosenSubCategory("Countries and Captitals")
        }

        cvGeographicFactsAndTrivia.setOnClickListener{
            chosenSubCategory("Geographic facts and Trivia")
        }

        cvFamousHistoricalFigures.setOnClickListener{
            chosenSubCategory("Famous Historical Figures")
        }

        cvHistoricalEvents.setOnClickListener{
            chosenSubCategory("Historical Events")
        }

        cvEntertainment.setOnClickListener{
            chosenCategory("Entertainment")
        }

        cvMovies.setOnClickListener{
            chosenSubCategory("Movies")
        }

        cvTvShows.setOnClickListener{
            chosenSubCategory("Tv Shows")
        }

        cvMusic.setOnClickListener{
            chosenSubCategory("Music")
        }

        cvBooksAndLiterature.setOnClickListener{
            chosenSubCategory("Books and Literature")
        }

        cvCelebrityAndPopCulture.setOnClickListener{
            chosenSubCategory("Celebrity and Pop Culture")
        }

        cvVideoGames.setOnClickListener{
            chosenSubCategory("Video Games")
        }

        cvSports.setOnClickListener{
            chosenCategory("Sports")
        }

        cvSoccer.setOnClickListener{
            chosenSubCategory("Soccer")
        }

        cvUnusualSportsFacts.setOnClickListener{
            chosenSubCategory("Unusual sports facts")
        }

        cvMemorableSportingEvents.setOnClickListener{
            chosenSubCategory("Memorable sporting events")
        }

        cvFoodAndDrinks.setOnClickListener{
            chosenCategory("Food and Drinks")
        }

        cvCuisine.setOnClickListener{
            chosenSubCategory("Cuisine")
        }

        cvIngredients.setOnClickListener{
            chosenSubCategory("Ingredients")
        }

        cvBeverages.setOnClickListener{
            chosenSubCategory("Beverages")
        }

        cvFoodAndTrivia.setOnClickListener{
            chosenSubCategory("Food and Trivia")
        }

        btnBackCategory.setOnClickListener{
            hideEverything()
            txtViewSubCategories.text = "Choose a Subcategory!"
            showCategories()
            activity?.title = "Chose your category!"
            tvTitle.text = "Quizzor"
        }

        backButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }

        btnStartGame.setOnClickListener{
            hideEverything()
            when(txtViewSubCategories.text){
                "Trivia" -> {
                    saveDataToSharedPreferences("category", "Trivia")
                }
                "World Facts" -> {
                    saveDataToSharedPreferences("category", "World Facts")
                }
                "Famous Personalities" -> {
                    saveDataToSharedPreferences("category", "Famous Personalities")
                }
                "Physics" -> {
                    saveDataToSharedPreferences("category", "Physics")
                }
                "Chemistry" -> {
                    saveDataToSharedPreferences("category", "Chemistry")
                }
                "Biology" -> {
                    saveDataToSharedPreferences("category", "Biology")
                }
                "Computer Science" -> {
                    saveDataToSharedPreferences("category", "Computer Science")
                }
                "Countries and Captitals" -> {
                    saveDataToSharedPreferences("category", "Countries and Captitals")
                }
                "Geographic facts and Trivia" -> {
                    saveDataToSharedPreferences("category", "Geographic facts and Trivia")
                }
                "Famous Historical Figures" -> {
                    saveDataToSharedPreferences("category", "Famous Historical Figures")
                }
                "Historical Events" -> {
                    saveDataToSharedPreferences("category", "Historical Events")
                }
                "Movies" -> {
                    saveDataToSharedPreferences("category", "Movies")
                }
                "Tv Shows" -> {
                    saveDataToSharedPreferences("category", "Tv Shows")
                }
                "Music" -> {
                    saveDataToSharedPreferences("category", "Music")
                }
                "Books and Literature" -> {
                    saveDataToSharedPreferences("category", "Books and Literature")
                }
                "Celebrity and Pop Culture" -> {
                    saveDataToSharedPreferences("category", "Celebrity and Pop Culture")
                }
                "Video Games" -> {
                    saveDataToSharedPreferences("category", "Video Games")
                }
                "Soccer" -> {
                    saveDataToSharedPreferences("category", "Soccer")
                }
                "Unusual sports facts" -> {
                    saveDataToSharedPreferences("category", "Unusual sports facts")
                }
                "Memorable sporting events" -> {
                    saveDataToSharedPreferences("category", "Memorable sporting events")
                }
                "Cuisine" -> {
                    saveDataToSharedPreferences("category", "Cuisine")
                }
                "Ingredients" -> {
                    saveDataToSharedPreferences("category", "Ingredients")
                }
                "Beverages" -> {
                    saveDataToSharedPreferences("category", "Beverages")
                }
                "Food and Trivia" -> {
                    saveDataToSharedPreferences("category", "Food and Trivia")
                }
            }
            goToScreen("startGame")
        }



        btnGoToChooseSubCategory.setOnClickListener{
            hideEverything()
            when(txtChosenCategory.text) {
                "General Knowledge" -> {
                    txtViewSubCategories.visibility = View.VISIBLE
                    llGeneralKnowledgeSubs.visibility = View.VISIBLE
                    llBackCategory.visibility = View.VISIBLE
                }
                "Science and Technology" -> {
                    txtViewSubCategories.visibility = View.VISIBLE
                    ll1stRowOfScienceAndTechnologySubs.visibility = View.VISIBLE
                    ll2ndRowOfScienceAndTechnologySubs.visibility = View.VISIBLE
                    llBackCategory.visibility = View.VISIBLE
                }
                "History and Geography" -> {
                    txtViewSubCategories.visibility = View.VISIBLE
                    ll1stRowOfHistoryAndGeographySubs.visibility = View.VISIBLE
                    ll2ndRowOfHistoryAndGeographySubs.visibility = View.VISIBLE
                    llBackCategory.visibility = View.VISIBLE
                }
                "Entertainment" -> {
                    txtViewSubCategories.visibility = View.VISIBLE
                    ll1stRowOfEntertainmentSubs.visibility = View.VISIBLE
                    ll2ndRowOfEntertainmentSubs.visibility = View.VISIBLE
                    llBackCategory.visibility = View.VISIBLE
                }
                "Sports" -> {
                    txtViewSubCategories.visibility = View.VISIBLE
                    llRowOfSportsSubs.visibility = View.VISIBLE
                    llBackCategory.visibility = View.VISIBLE
                }
                "Food and Drinks" -> {
                    txtViewSubCategories.visibility = View.VISIBLE
                    ll1stRowOfFoodAndDrinksSubs.visibility = View.VISIBLE
                    ll2ndRowOfFoodAndDrinksSubs.visibility = View.VISIBLE
                    llBackCategory.visibility = View.VISIBLE
                }
            }
            hideStartButton()
        }
        // THIS TO THE END OF EACH BUTTON
            /*goToScreen("startGame")*/

        return view
    }

    private fun hideStartButton(){
        btnStartGame.visibility = View.GONE
    }

    private fun showStartButton(){
        btnStartGame.visibility = View.VISIBLE
    }

    private fun chosenCategory(category: String){
        hideEverything()
        when(category) {
            "General Knowledge" -> {
                txtViewSubCategories.visibility = View.VISIBLE
                llGeneralKnowledgeSubs.visibility = View.VISIBLE
                llBackCategory.visibility = View.VISIBLE
            }
            "Science and Technology" -> {
                txtViewSubCategories.visibility = View.VISIBLE
                ll1stRowOfScienceAndTechnologySubs.visibility = View.VISIBLE
                ll2ndRowOfScienceAndTechnologySubs.visibility = View.VISIBLE
                llBackCategory.visibility = View.VISIBLE
            }
            "History and Geography" -> {
                txtViewSubCategories.visibility = View.VISIBLE
                ll1stRowOfHistoryAndGeographySubs.visibility = View.VISIBLE
                ll2ndRowOfHistoryAndGeographySubs.visibility = View.VISIBLE
                llBackCategory.visibility = View.VISIBLE
            }
            "Entertainment" -> {
                txtViewSubCategories.visibility = View.VISIBLE
                ll1stRowOfEntertainmentSubs.visibility = View.VISIBLE
                ll2ndRowOfEntertainmentSubs.visibility = View.VISIBLE
                llBackCategory.visibility = View.VISIBLE
            }
            "Sports" -> {
                txtViewSubCategories.visibility = View.VISIBLE
                llRowOfSportsSubs.visibility = View.VISIBLE
                llBackCategory.visibility = View.VISIBLE
            }
            "Food and Drinks" -> {
                txtViewSubCategories.visibility = View.VISIBLE
                ll1stRowOfFoodAndDrinksSubs.visibility = View.VISIBLE
                ll2ndRowOfFoodAndDrinksSubs.visibility = View.VISIBLE
                llBackCategory.visibility = View.VISIBLE
            }
        }
        activity?.title = "Chose your subcategory!"
        tvTitle.text = category
        hideStartButton()
    }

    private fun chosenSubCategory(subCategory: String){
        hideEverything()
        saveDataToSharedPreferences("category", subCategory)

        txtViewSubCategories.text = "Choose a Subcategory!"
        goToScreen("startGame")
    }

    private fun showCategories() {
        txtChosenCategory.text = "Choose a Category!"
        txtChosenCategory.visibility = View.VISIBLE
        ll1stRowOfCategories.visibility = View.VISIBLE
        ll2ndRowOfCategories.visibility = View.VISIBLE
        btnBackToMainScreen.visibility = View.VISIBLE
        backButton.visibility = View.VISIBLE
    }

    private fun hideEverything() {
        ll1stRowOfCategories.visibility = View.GONE
        ll2ndRowOfCategories.visibility = View.GONE
        txtViewSubCategories.visibility = View.GONE
        llGeneralKnowledgeSubs.visibility = View.GONE
        ll1stRowOfScienceAndTechnologySubs.visibility = View.GONE
        ll2ndRowOfScienceAndTechnologySubs.visibility = View.GONE
        ll1stRowOfHistoryAndGeographySubs.visibility = View.GONE
        ll2ndRowOfHistoryAndGeographySubs.visibility = View.GONE
        ll1stRowOfEntertainmentSubs.visibility = View.GONE
        ll2ndRowOfEntertainmentSubs.visibility = View.GONE
        llBackCategory.visibility = View.GONE
        llRowOfSportsSubs.visibility = View.GONE
        ll1stRowOfFoodAndDrinksSubs.visibility = View.GONE
        ll2ndRowOfFoodAndDrinksSubs.visibility = View.GONE
        btnGoToChooseSubCategory.visibility = View.GONE
        txtChosenCategory.visibility = View.GONE
        btnBackToMainScreen.visibility = View.GONE

    }

    private fun goToScreen(fragment: String) {
        when (fragment) {
            "startGame" -> {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, StandardQuizFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            PlayFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private fun getDataFromSharedPreferences(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }
    private fun saveDataToSharedPreferences(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }


    private fun setLanguagePreferencesToView(language: String){
        when (language){
            "en" ->{
                tvGeneralKnowledge.text = "General Knowledge"
                tvScienceAndTechnology.text = "Science & Technology"
                tvHistoryAndGeography.text = "History & Geography"
                tvEntertainment.text = "Entertainment"
                tvSports.text = "Sports"
                tvFoodAndDrinks.text = "Food & Drinks"
            }
            "ro" -> {
                tvGeneralKnowledge.text = "Cunoștințe generale"
                tvScienceAndTechnology.text = "Știință și tehnologie"
                tvHistoryAndGeography.text = "Istorie și Geografie"
                tvEntertainment.text = "Divertisment"
                tvSports.text = "Sporturi"
                tvFoodAndDrinks.text = "Mâncare și băuturi"
            }
            "de" -> {
                tvGeneralKnowledge.text = "Allgemeinwissen"
                tvScienceAndTechnology.text = "Wissenschaft & Technik"
                tvHistoryAndGeography.text = "Geschichte & Geographie"
                tvEntertainment.text = "Unterhaltung"
                tvSports.text = "Sport"
                tvFoodAndDrinks.text = "Essen & Trinken"
            }
            "fr" -> {
                tvGeneralKnowledge.text = "Connaissances générales"
                tvScienceAndTechnology.text = "Science et technologie"
                tvHistoryAndGeography.text = "Histoire et géographie"
                tvEntertainment.text = "Divertissement"
                tvSports.text = "Sports"
                tvFoodAndDrinks.text = "Nourriture et boissons"
            }
            "hu" -> {
                tvGeneralKnowledge.text = "Általános ismeretek"
                tvScienceAndTechnology.text = "Tudomány és technológia"
                tvHistoryAndGeography.text = "Történelem és földrajz"
                tvEntertainment.text = "Szórakozás"
                tvSports.text = "Sport"
                tvFoodAndDrinks.text = "Ételek és italok"
            }
            "jp" -> {
                tvGeneralKnowledge.text = "一般常識"
                tvScienceAndTechnology.text = "科学と技術"
                tvHistoryAndGeography.text = "歴史と地理"
                tvEntertainment.text = "エンターテインメント"
                tvSports.text = "スポーツ"
                tvFoodAndDrinks.text = "フード＆ドリンク"
            }
        }
    }
}