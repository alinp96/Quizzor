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

    private lateinit var tvTrivia: TextView
    private lateinit var tvWorldFacts: TextView
    private lateinit var tvFamousCelebrities: TextView
    private lateinit var tvPhysics: TextView
    private lateinit var tvChemistry: TextView
    private lateinit var tvBiology: TextView
    private lateinit var tvComputerScience: TextView
    private lateinit var tvCountriesAndCapitals: TextView
    private lateinit var tvGeographicFactsAndTrivia: TextView
    private lateinit var tvFamousHistoricalFigures: TextView
    private lateinit var tvHistoricalEvents: TextView
    private lateinit var tvMovies: TextView
    private lateinit var tvTvShows: TextView
    private lateinit var tvMusic: TextView
    private lateinit var tvBooksAndLiterature: TextView
    private lateinit var tvCelebrityAndPopCulture: TextView
    private lateinit var tvVideoGames: TextView
    private lateinit var tvSoccer: TextView
    private lateinit var tvUnusualSportsFacts: TextView
    private lateinit var tvMemorableSportingEvents: TextView
    private lateinit var tvCuisine: TextView
    private lateinit var tvIngredients: TextView
    private lateinit var tvBeverages: TextView
    private lateinit var tvFoodAndTrivia: TextView

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
        (activity as MainActivity).switchToGeneralMusic()
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

        tvTrivia = view.findViewById<TextView>(R.id.tv_trivia)
        tvWorldFacts = view.findViewById<TextView>(R.id.tv_worldFacts)
        tvFamousCelebrities = view.findViewById<TextView>(R.id.tv_famousCelebrities)
        tvPhysics = view.findViewById<TextView>(R.id.tv_physics)
        tvChemistry = view.findViewById<TextView>(R.id.tv_chemistry)
        tvBiology = view.findViewById<TextView>(R.id.tv_biology)
        tvComputerScience = view.findViewById<TextView>(R.id.tv_computerScience)
        tvCountriesAndCapitals = view.findViewById<TextView>(R.id.tv_countriesAndCapital)
        tvGeographicFactsAndTrivia = view.findViewById<TextView>(R.id.tv_geographicFactsAndTrivia)
        tvFamousHistoricalFigures = view.findViewById<TextView>(R.id.tv_famousHistoricalFigures)
        tvHistoricalEvents = view.findViewById<TextView>(R.id.tv_historicalEvents)
        tvMovies = view.findViewById<TextView>(R.id.tv_movies)
        tvTvShows = view.findViewById<TextView>(R.id.tv_tvShows)
        tvMusic = view.findViewById<TextView>(R.id.tv_music)
        tvBooksAndLiterature = view.findViewById<TextView>(R.id.tv_booksAndLiterature)
        tvCelebrityAndPopCulture = view.findViewById<TextView>(R.id.tv_celebrityAndPopCulture)
        tvVideoGames = view.findViewById<TextView>(R.id.tv_videoGames)
        tvSoccer = view.findViewById<TextView>(R.id.tv_soccer)
        tvUnusualSportsFacts = view.findViewById<TextView>(R.id.tv_unusualSportsFacts)
        tvMemorableSportingEvents = view.findViewById<TextView>(R.id.tv_memorableSportingEvents)
        tvCuisine = view.findViewById<TextView>(R.id.tv_cuisine)
        tvIngredients = view.findViewById<TextView>(R.id.tv_ingredients)
        tvBeverages = view.findViewById<TextView>(R.id.tv_beverages)
        tvFoodAndTrivia = view.findViewById<TextView>(R.id.tv_foodTrivia)

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
            (activity as MainActivity).playButtonClickSound()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }

        cvGeneralKnowledge.setOnClickListener{
            chosenCategory("General Knowledge", language)
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
            chosenCategory("Science and Technology", language)
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
            chosenCategory("History and Geography", language)
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
            chosenCategory("Entertainment", language)
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
            chosenCategory("Sports", language)
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
            chosenCategory("Food and Drinks", language)
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
            (activity as MainActivity).playButtonClickSound()
            hideEverything()
            //txtViewSubCategories.text = "Choose a Subcategory!"
            showCategories()
            //activity?.title = "Chose your category!"
            tvTitle.text = "Quizzor"
        }

        backButton.setOnClickListener {
            (activity as MainActivity).playButtonClickSound()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }

        btnStartGame.setOnClickListener{
            (activity as MainActivity).playButtonClickSound()
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
            (activity as MainActivity).playButtonClickSound()
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

    private fun chosenCategory(category: String, language: String){
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
        //tvTitle.text = category
        tvTitle.text = categorySelectedText(category, language)
        hideStartButton()
    }

    private fun categorySelectedText(category: String, language: String): String {
        var renamedCategory: String = ""
        when (language) {
            "en" -> {
                when(category){
                    "General Knowledge" -> {
                        renamedCategory = category
                    }
                    "Science and Technology" -> {
                        renamedCategory = category
                    }
                    "History and Geography" -> {
                        renamedCategory = category
                    }
                    "Entertainment" -> {
                        renamedCategory = category
                    }
                    "Sports" -> {
                        renamedCategory = category
                    }
                    "Food and Drinks" -> {
                        renamedCategory = category
                    }
                }
            }
            "ro" -> {
                when(category){
                    "General Knowledge" -> {
                        renamedCategory = "Cunoștințe generale"
                    }
                    "Science and Technology" -> {
                        renamedCategory = "Știință și tehnologie"
                    }
                    "History and Geography" -> {
                        renamedCategory = "Istorie și Geografie"
                    }
                    "Entertainment" -> {
                        renamedCategory = "Divertisment"
                    }
                    "Sports" -> {
                        renamedCategory = "Sporturi"
                    }
                    "Food and Drinks" -> {
                        renamedCategory = "Mâncare și băuturi"
                    }
                }
            }
            "de" -> {
                when(category){
                    "General Knowledge" -> {
                        renamedCategory = "Allgemeinwissen"
                    }
                    "Science and Technology" -> {
                        renamedCategory = "Wissenschaft & Technik"
                    }
                    "History and Geography" -> {
                        renamedCategory = "Geschichte & Geographie"
                    }
                    "Entertainment" -> {
                        renamedCategory = "Unterhaltung"
                    }
                    "Sports" -> {
                        renamedCategory = "Sport"
                    }
                    "Food and Drinks" -> {
                        renamedCategory = "Essen & Trinken"
                    }
                }
            }
            "fr" -> {
                when(category){
                    "General Knowledge" -> {
                        renamedCategory = "Connaissances générales"
                    }
                    "Science and Technology" -> {
                        renamedCategory = "Science et technologie"
                    }
                    "History and Geography" -> {
                        renamedCategory = "Histoire et géographie"
                    }
                    "Entertainment" -> {
                        renamedCategory = "Divertissement"
                    }
                    "Sports" -> {
                        renamedCategory = "Sports"
                    }
                    "Food and Drinks" -> {
                        renamedCategory = "Nourriture et boissons"
                    }
                }
            }
            "hu" -> {
                when(category){
                    "General Knowledge" -> {
                        renamedCategory = "Általános ismeretek"
                    }
                    "Science and Technology" -> {
                        renamedCategory = "Tudomány és technológia"
                    }
                    "History and Geography" -> {
                        renamedCategory = "Történelem és földrajz"
                    }
                    "Entertainment" -> {
                        renamedCategory = "Szórakozás"
                    }
                    "Sports" -> {
                        renamedCategory = "Sport"
                    }
                    "Food and Drinks" -> {
                        renamedCategory = "Ételek és italok"
                    }
                }
            }
            "jp" -> {
                when(category){
                    "General Knowledge" -> {
                        renamedCategory = "一般常識"
                    }
                    "Science and Technology" -> {
                        renamedCategory = "科学と技術"
                    }
                    "History and Geography" -> {
                        renamedCategory = "歴史と地理"
                    }
                    "Entertainment" -> {
                        renamedCategory = "エンターテインメント"
                    }
                    "Sports" -> {
                        renamedCategory = "スポーツ"
                    }
                    "Food and Drinks" -> {
                        renamedCategory = "フード＆ドリンク"
                    }
                }
            }
        }
        return renamedCategory
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
                backButton.text = "Back"
                txtViewSubCategories.text = "Choose a subcategory!"

                tvGeneralKnowledge.text = "General Knowledge"
                tvScienceAndTechnology.text = "Science & Technology"
                tvHistoryAndGeography.text = "History & Geography"
                tvEntertainment.text = "Entertainment"
                tvSports.text = "Sports"
                tvFoodAndDrinks.text = "Food & Drinks"

                tvTrivia.text = "Trivia"
                tvWorldFacts.text = "World Facts"
                tvFamousCelebrities.text = "Famous Celebrities"
                tvPhysics.text = "Physics"
                tvChemistry.text = "Chemistry"
                tvBiology.text = "Biology"
                tvComputerScience.text = "Computer Science"
                tvCountriesAndCapitals.text = "Countries & Capitals"
                tvGeographicFactsAndTrivia.text = "Geographic facts & trivia"
                tvFamousHistoricalFigures.text = "Famous historical figures"
                tvHistoricalEvents.text = "Historical events"
                tvMovies.text = "Movies"
                tvTvShows.text = "TV Shows"
                tvMusic.text = "Music"
                tvBooksAndLiterature.text = "Books & Literature"
                tvCelebrityAndPopCulture.text = "Celebrity & Pop culture"
                tvVideoGames.text = "Video games"
                tvSoccer.text = "Soccer"
                tvUnusualSportsFacts.text = "Unusual Sports Facts"
                tvMemorableSportingEvents.text = "Memorable Sporting Events"
                tvCuisine.text = "Cuisine"
                tvIngredients.text = "Ingredients"
                tvBeverages.text = "Beverages"
                tvFoodAndTrivia.text = "Food trivia"
            }
            "ro" -> {
                backButton.text = "Înapoi"
                txtViewSubCategories.text = "Alege o subcategorie!"

                tvGeneralKnowledge.text = "Cunoștințe generale"
                tvScienceAndTechnology.text = "Știință și tehnologie"
                tvHistoryAndGeography.text = "Istorie și Geografie"
                tvEntertainment.text = "Divertisment"
                tvSports.text = "Sporturi"
                tvFoodAndDrinks.text = "Mâncare și băuturi"

                tvTrivia.text = "Curiozități"
                tvWorldFacts.text = "Fapte din lume"
                tvFamousCelebrities.text = "Personalități celebre"
                tvPhysics.text = "Fizică"
                tvChemistry.text = "Chimie"
                tvBiology.text = "Biologie"
                tvComputerScience.text = "Informatică"
                tvCountriesAndCapitals.text = "Țări și capitale"
                tvGeographicFactsAndTrivia.text = "Date geografice și curiozități"
                tvFamousHistoricalFigures.text = "Personalități istorice celebre"
                tvHistoricalEvents.text = "Evenimente istorice"
                tvMovies.text = "Filme"
                tvTvShows.text = "Emisiuni TV"
                tvMusic.text = "Muzică"
                tvBooksAndLiterature.text = "Cărți și literatură"
                tvCelebrityAndPopCulture.text = "Celebritate și cultură populară"
                tvVideoGames.text = "Jocuri video"
                tvSoccer.text = "Fotbal"
                tvUnusualSportsFacts.text = "Fapte sportive neobișnuite"
                tvMemorableSportingEvents.text = "Evenimente sportive memorabile"
                tvCuisine.text = "Bucătărie"
                tvIngredients.text = "Ingrediente"
                tvBeverages.text = "Băuturi"
                tvFoodAndTrivia.text = "Curiozități alimentare"
            }
            "de" -> {
                backButton.text = "Back"
                txtViewSubCategories.text = "Wählen Sie eine Unterkategorie!"

                tvGeneralKnowledge.text = "Allgemeinwissen"
                tvScienceAndTechnology.text = "Wissenschaft & Technik"
                tvHistoryAndGeography.text = "Geschichte & Geographie"
                tvEntertainment.text = "Unterhaltung"
                tvSports.text = "Sport"
                tvFoodAndDrinks.text = "Essen & Trinken"

                tvTrivia.text = "Quizfragen"
                tvWorldFacts.text = "Weltfakten"
                tvFamousCelebrities.text = "Berühmte Persönlichkeiten"
                tvPhysics.text = "Physik"
                tvChemistry.text = "Chemie"
                tvBiology.text = "Biologie"
                tvComputerScience.text = "Informatik"
                tvCountriesAndCapitals.text = "Länder & Hauptstädte"
                tvGeographicFactsAndTrivia.text = "Geografische Fakten & Wissenswertes"
                tvFamousHistoricalFigures.text = "Berühmte historische Persönlichkeiten"
                tvHistoricalEvents.text = "Historische Ereignisse"
                tvMovies.text = "Filme"
                tvTvShows.text = "Fernsehsendungen"
                tvMusic.text = "Musik"
                tvBooksAndLiterature.text = "Bücher & Literatur"
                tvCelebrityAndPopCulture.text = "Berühmtheit & Pop-Kultur"
                tvVideoGames.text = "Videospiele"
                tvSoccer.text = "Fußball"
                tvUnusualSportsFacts.text = "Ungewöhnliche Sportfakten"
                tvMemorableSportingEvents.text = "Denkwürdige Sportereignisse"
                tvCuisine.text = "Kulinarisches"
                tvIngredients.text = "Zutaten"
                tvBeverages.text = "Getränke"
                tvFoodAndTrivia.text = "Lebensmittel-Trivia"
            }
            "fr" -> {
                backButton.text = "Retour"
                txtViewSubCategories.text = "Choisissez une sous-catégorie!"

                tvGeneralKnowledge.text = "Connaissances générales"
                tvScienceAndTechnology.text = "Science et technologie"
                tvHistoryAndGeography.text = "Histoire et géographie"
                tvEntertainment.text = "Divertissement"
                tvSports.text = "Sports"
                tvFoodAndDrinks.text = "Nourriture et boissons"

                tvTrivia.text = "Anecdotes"
                tvWorldFacts.text = "Faits sur le monde"
                tvFamousCelebrities.text = "Célébrités célèbres"
                tvPhysics.text = "Physique"
                tvChemistry.text = "Chimie"
                tvBiology.text = "Biologie"
                tvComputerScience.text = "Informatique"
                tvCountriesAndCapitals.text = "Pays et capitales"
                tvGeographicFactsAndTrivia.text = "Faits géographiques et anecdotes"
                tvFamousHistoricalFigures.text = "Personnages historiques célèbres"
                tvHistoricalEvents.text = "Evénements historiques"
                tvMovies.text = "Films"
                tvTvShows.text = "Émissions de télévision"
                tvMusic.text = "Musique"
                tvBooksAndLiterature.text = "Livres et littérature"
                tvCelebrityAndPopCulture.text = "Célébrité et culture pop"
                tvVideoGames.text = "Jeux vidéo"
                tvSoccer.text = "Football"
                tvUnusualSportsFacts.text = "Faits sportifs inhabituels"
                tvMemorableSportingEvents.text = "Événements sportifs mémorables"
                tvCuisine.text = "Cuisine"
                tvIngredients.text = "Ingrédients"
                tvBeverages.text = "Boissons"
                tvFoodAndTrivia.text = "Questions sur l'alimentation"
            }
            "hu" -> {
                backButton.text = "Vissza"
                txtViewSubCategories.text = "Válasszon alkategóriát!"

                tvGeneralKnowledge.text = "Általános ismeretek"
                tvScienceAndTechnology.text = "Tudomány és technológia"
                tvHistoryAndGeography.text = "Történelem és földrajz"
                tvEntertainment.text = "Szórakozás"
                tvSports.text = "Sport"
                tvFoodAndDrinks.text = "Ételek és italok"

                tvTrivia.text = "Kvíz"
                tvWorldFacts.text = "Világtények"
                tvFamousCelebrities.text = "Híres hírességek"
                tvPhysics.text = "Fizika"
                tvChemistry.text = "Kémia"
                tvBiology.text = "Biológia"
                tvComputerScience.text = "Számítástechnika"
                tvCountriesAndCapitals.text = "Országok és fővárosok"
                tvGeographicFactsAndTrivia.text = "Földrajzi tények és kvízek"
                tvFamousHistoricalFigures.text = "Híres történelmi személyiségek"
                tvHistoricalEvents.text = "Történelmi események"
                tvMovies.text = "Filmek"
                tvTvShows.text = "Tévéműsorok"
                tvMusic.text = "Zene"
                tvBooksAndLiterature.text = "Könyvek és irodalom"
                tvCelebrityAndPopCulture.text = "Hírességek és popkultúra"
                tvVideoGames.text = "Videojátékok"
                tvSoccer.text = "Labdarúgás"
                tvUnusualSportsFacts.text = "Szokatlan sporttények"
                tvMemorableSportingEvents.text = "Emlékezetes sportesemények"
                tvCuisine.text = "Konyha"
                tvIngredients.text = "Hozzávalók"
                tvBeverages.text = "Italok"
                tvFoodAndTrivia.text = "Élelmiszerek és kvízek"
            }
            "jp" -> {
                backButton.text = "バック"
                txtViewSubCategories.text = "サブカテゴリーを選んでください！"

                tvGeneralKnowledge.text = "一般常識"
                tvScienceAndTechnology.text = "科学と技術"
                tvHistoryAndGeography.text = "歴史と地理"
                tvEntertainment.text = "エンターテインメント"
                tvSports.text = "スポーツ"
                tvFoodAndDrinks.text = "フード＆ドリンク"

                tvTrivia.text = "トリビア"
                tvWorldFacts.text = "世界の事実"
                tvFamousCelebrities.text = "有名な有名人"
                tvPhysics.text = "物理学"
                tvChemistry.text = "化学"
                tvBiology.text = "生物学"
                tvComputerScience.text = "コンピューターサイエンス"
                tvCountriesAndCapitals.text = "国と首都"
                tvGeographicFactsAndTrivia.text = "地理的事実とトリビア"
                tvFamousHistoricalFigures.text = "歴史上の有名な人物"
                tvHistoricalEvents.text = "歴史上の出来事"
                tvMovies.text = "映画"
                tvTvShows.text = "テレビ番組"
                tvMusic.text = "音楽"
                tvBooksAndLiterature.text = "書籍・文学"
                tvCelebrityAndPopCulture.text = "セレブ＆ポップカルチャー"
                tvVideoGames.text = "ビデオゲーム"
                tvSoccer.text = "サッカー"
                tvUnusualSportsFacts.text = "珍しいスポーツの事実"
                tvMemorableSportingEvents.text = "記憶に残るスポーツイベント"
                tvCuisine.text = "料理"
                tvIngredients.text = "食材"
                tvBeverages.text = "飲み物"
                tvFoodAndTrivia.text = "食べ物の豆知識"
            }
        }
    }
}