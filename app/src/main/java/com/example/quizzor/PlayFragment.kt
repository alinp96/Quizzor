package com.example.quizzor

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

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
    private lateinit var llBackCategory: View
    private lateinit var txtViewSubCategories: View
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

    private lateinit var btnGeneralKnowledge: ImageButton
    private lateinit var btnScienceAndTechnology: ImageButton
    private lateinit var btnHistoryAndGeography: ImageButton
    private lateinit var btnEntertainment: ImageButton
    private lateinit var btnSports: ImageButton
    private lateinit var btnFoodAndDrinks: ImageButton

    private lateinit var btnBackCategory: Button
    private lateinit var btnGoToChooseSubCategory: Button
    private lateinit var btnTrivia: ImageButton
    private lateinit var btnWorldFacts: ImageButton
    private lateinit var btnFamousPersonalities: ImageButton
    private lateinit var btnPhysics: ImageButton
    private lateinit var btnChemistry: ImageButton
    private lateinit var btnBiology: ImageButton
    private lateinit var btnComputerScience: ImageButton
    private lateinit var btnContriesAndCapitals: ImageButton
    private lateinit var btnGeographicFactsAndTrivia: ImageButton
    private lateinit var btnFamousHistoricalFigures: ImageButton
    private lateinit var btnHistoricalEvents: ImageButton
    private lateinit var btnMovies: ImageButton
    private lateinit var btnTvShows: ImageButton
    private lateinit var btnMusic: ImageButton
    private lateinit var btnBooksAndLiterature: ImageButton
    private lateinit var btnCelebrityAndPopCulture: ImageButton
    private lateinit var btnVideoGames: ImageButton
    private lateinit var btnSoccer: ImageButton
    private lateinit var btnUnusualSportsFacts: ImageButton
    private lateinit var btnMemorableSportingEvents: ImageButton
    private lateinit var btnCuisine: ImageButton
    private lateinit var btnIngredients: ImageButton
    private lateinit var btnBeverages: ImageButton
    private lateinit var btnFoodAndTrivia: ImageButton

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

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_play, container, false)
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

        btnGoToChooseSubCategory = view.findViewById<Button>(R.id.btnGoToChooseSubCategory)
        btnBackCategory = view.findViewById<Button>(R.id.btnBackCategory)
        btnGeneralKnowledge = view.findViewById<ImageButton>(R.id.btnGeneralKnowledge)
        btnTrivia = view.findViewById<ImageButton>(R.id.btnTrivia)
        btnWorldFacts = view.findViewById<ImageButton>(R.id.btnWorldFacts)
        btnFamousPersonalities = view.findViewById<ImageButton>(R.id.btnFamousCelebrities)
        btnScienceAndTechnology = view.findViewById<ImageButton>(R.id.btnScienceAndTechnology)
        btnPhysics = view.findViewById<ImageButton>(R.id.btnPhysics)
        btnChemistry = view.findViewById<ImageButton>(R.id.btnChemistry)
        btnBiology = view.findViewById<ImageButton>(R.id.btnBiology)
        btnComputerScience = view.findViewById<ImageButton>(R.id.btnComputerScience)
        btnHistoryAndGeography = view.findViewById<ImageButton>(R.id.btnHistoryAndGeography)
        btnEntertainment = view.findViewById<ImageButton>(R.id.btnEntertainment)
        btnSports = view.findViewById<ImageButton>(R.id.btnSports)
        btnFoodAndDrinks = view.findViewById<ImageButton>(R.id.btnFoodAndDrinks)
        btnContriesAndCapitals = view.findViewById<ImageButton>(R.id.btnContriesAndCapitals)
        btnGeographicFactsAndTrivia = view.findViewById<ImageButton>(R.id.btnGeographicFactsAndTrivia)
        btnFamousHistoricalFigures = view.findViewById<ImageButton>(R.id.btnFamousHistoricalFigures)
        btnHistoricalEvents = view.findViewById<ImageButton>(R.id.btnHistoricalEvents)
        btnMovies = view.findViewById<ImageButton>(R.id.btnMovies)
        btnTvShows = view.findViewById<ImageButton>(R.id.btnTvShows)
        btnMusic = view.findViewById<ImageButton>(R.id.btnMusic)
        btnBooksAndLiterature = view.findViewById<ImageButton>(R.id.btnBooksAndLiterature)
        btnCelebrityAndPopCulture = view.findViewById<ImageButton>(R.id.btnCelebrityAndPopCulture)
        btnVideoGames = view.findViewById<ImageButton>(R.id.btnVideoGames)
        btnSoccer = view.findViewById<ImageButton>(R.id.btnSoccer)
        btnUnusualSportsFacts = view.findViewById<ImageButton>(R.id.btnUnusualSportsFacts)
        btnMemorableSportingEvents = view.findViewById<ImageButton>(R.id.btnMemorableSportingEvents)
        btnCuisine = view.findViewById<ImageButton>(R.id.btnCuisine)
        btnIngredients = view.findViewById<ImageButton>(R.id.btnIngredients)
        btnBeverages = view.findViewById<ImageButton>(R.id.btnBeverages)
        btnFoodAndTrivia = view.findViewById<ImageButton>(R.id.btnFoodAndTrivia)

        //val adapter = setLanguagePreferencesToView(language, view)


        // Show categories
        hideEverything()
        showCategories()

        btnGeneralKnowledge.setOnClickListener{
            txtChosenCategory.text = "General Knowledge"
            txtChosenCategory.visibility = View.VISIBLE
            btnGoToChooseSubCategory.visibility = View.VISIBLE
        }

        btnTrivia.setOnClickListener{
            saveDataToSharedPreferences("category", "Trivia")
            goToScreen("startGame")
        }

        btnWorldFacts.setOnClickListener{
            saveDataToSharedPreferences("category", "World Facts")
            goToScreen("startGame")
        }

        btnFamousPersonalities.setOnClickListener{
            saveDataToSharedPreferences("category", "Famous Personalities")
            goToScreen("startGame")
        }

        btnScienceAndTechnology.setOnClickListener{
            txtChosenCategory.text = "Science and Technology"
            txtChosenCategory.visibility = View.VISIBLE
            btnGoToChooseSubCategory.visibility = View.VISIBLE
        }

        btnPhysics.setOnClickListener{
            saveDataToSharedPreferences("category", "Physics")
            goToScreen("startGame")
        }

        btnChemistry.setOnClickListener{
            saveDataToSharedPreferences("category", "Chemistry")
            goToScreen("startGame")
        }

        btnBiology.setOnClickListener{
            saveDataToSharedPreferences("category", "Biology")
            goToScreen("startGame")
        }

        btnComputerScience.setOnClickListener{
            saveDataToSharedPreferences("category", "Computer Science")
            goToScreen("startGame")
        }

        btnHistoryAndGeography.setOnClickListener{
            txtChosenCategory.text = "History and Geography"
            txtChosenCategory.visibility = View.VISIBLE
            btnGoToChooseSubCategory.visibility = View.VISIBLE
        }

        btnContriesAndCapitals.setOnClickListener{
            saveDataToSharedPreferences("category", "Countries and Captitals")
            goToScreen("startGame")
        }

        btnGeographicFactsAndTrivia.setOnClickListener{
            saveDataToSharedPreferences("category", "Geographic facts and Trivia")
            goToScreen("startGame")
        }

        btnFamousHistoricalFigures.setOnClickListener{
            saveDataToSharedPreferences("category", "Famous Historical Figures")
            goToScreen("startGame")
        }

        btnHistoricalEvents.setOnClickListener{
            saveDataToSharedPreferences("category", "Historical Events")
            goToScreen("startGame")
        }

        btnEntertainment.setOnClickListener{
            txtChosenCategory.text = "Entertainment"
            txtChosenCategory.visibility = View.VISIBLE
            btnGoToChooseSubCategory.visibility = View.VISIBLE
        }

        btnMovies.setOnClickListener{
            saveDataToSharedPreferences("category", "Movies")
            goToScreen("startGame")
        }

        btnTvShows.setOnClickListener{
            saveDataToSharedPreferences("category", "Tv Shows")
            goToScreen("startGame")
        }

        btnMusic.setOnClickListener{
            saveDataToSharedPreferences("category", "Music")
            goToScreen("startGame")
        }

        btnBooksAndLiterature.setOnClickListener{
            saveDataToSharedPreferences("category", "Books and Literature")
            goToScreen("startGame")
        }

        btnCelebrityAndPopCulture.setOnClickListener{
            saveDataToSharedPreferences("category", "Celebrity and Pop Culture")
            goToScreen("startGame")
        }

        btnVideoGames.setOnClickListener{
            saveDataToSharedPreferences("category", "Video Games")
            goToScreen("startGame")
        }

        btnSports.setOnClickListener{
            txtChosenCategory.text = "Sports"
            txtChosenCategory.visibility = View.VISIBLE
            btnGoToChooseSubCategory.visibility = View.VISIBLE
        }

        btnSoccer.setOnClickListener{
            saveDataToSharedPreferences("category", "Soccer")
            goToScreen("startGame")
        }

        btnUnusualSportsFacts.setOnClickListener{
            saveDataToSharedPreferences("category", "Unusual sports facts")
            goToScreen("startGame")
        }

        btnMemorableSportingEvents.setOnClickListener{
            saveDataToSharedPreferences("category", "Memorable sporting events")
            goToScreen("startGame")
        }

        btnFoodAndDrinks.setOnClickListener{
            txtChosenCategory.text = "Food and Drinks"
            txtChosenCategory.visibility = View.VISIBLE
            btnGoToChooseSubCategory.visibility = View.VISIBLE
        }

        btnCuisine.setOnClickListener{
            saveDataToSharedPreferences("category", "Cuisine")
            goToScreen("startGame")
        }

        btnIngredients.setOnClickListener{
            saveDataToSharedPreferences("category", "Ingredients")
            goToScreen("startGame")
        }

        btnBeverages.setOnClickListener{
            saveDataToSharedPreferences("category", "Beverages")
            goToScreen("startGame")
        }

        btnFoodAndTrivia.setOnClickListener{
            saveDataToSharedPreferences("category", "Food and Trivia")
            goToScreen("startGame")
        }

        btnBackCategory.setOnClickListener{
            hideEverything()
            showCategories()
        }

        btnGoToChooseSubCategory.setOnClickListener{
            hideEverything()
            if(txtChosenCategory.text == "General Knowledge"){
                hideEverything()
                txtViewSubCategories.visibility = View.VISIBLE
                llGeneralKnowledgeSubs.visibility = View.VISIBLE
                llBackCategory.visibility = View.VISIBLE
            } else if(txtChosenCategory.text == "Science and Technology"){
                hideEverything()
                txtViewSubCategories.visibility = View.VISIBLE
                ll1stRowOfScienceAndTechnologySubs.visibility = View.VISIBLE
                ll2ndRowOfScienceAndTechnologySubs.visibility = View.VISIBLE
                llBackCategory.visibility = View.VISIBLE
            } else if(txtChosenCategory.text == "History and Geography"){
                hideEverything()
                txtViewSubCategories.visibility = View.VISIBLE
                ll1stRowOfHistoryAndGeographySubs.visibility = View.VISIBLE
                ll2ndRowOfHistoryAndGeographySubs.visibility = View.VISIBLE
                llBackCategory.visibility = View.VISIBLE
            } else if(txtChosenCategory.text == "Entertainment"){
                hideEverything()
                txtViewSubCategories.visibility = View.VISIBLE
                ll1stRowOfEntertainmentSubs.visibility = View.VISIBLE
                ll2ndRowOfEntertainmentSubs.visibility = View.VISIBLE
                llBackCategory.visibility = View.VISIBLE
            } else if(txtChosenCategory.text == "Sports"){
                hideEverything()
                txtViewSubCategories.visibility = View.VISIBLE
                llRowOfSportsSubs.visibility = View.VISIBLE
                llBackCategory.visibility = View.VISIBLE
            } else if(txtChosenCategory.text == "Food and Drinks"){
                hideEverything()
                txtViewSubCategories.visibility = View.VISIBLE
                ll1stRowOfFoodAndDrinksSubs.visibility = View.VISIBLE
                ll2ndRowOfFoodAndDrinksSubs.visibility = View.VISIBLE
                llBackCategory.visibility = View.VISIBLE
            }
        }
        // THIS TO THE END OF EACH BUTTON
            /*goToScreen("startGame")*/

        return view
    }

    private fun showCategories() {
        txtChosenCategory.text = "Choose a Category!"
        txtChosenCategory.visibility = View.VISIBLE
        ll1stRowOfCategories.visibility = View.VISIBLE
        ll2ndRowOfCategories.visibility = View.VISIBLE
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


    private fun setLanguagePreferencesToView(language: String, view: View):  ArrayAdapter<CharSequence>{
        val btnBackCategory = view.findViewById<Button>(R.id.btnBackCategory)
        var adapter:  ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.quizCategoriesEn, android.R.layout.simple_spinner_item)
        when(language){
            "en"->{
                adapter = ArrayAdapter.createFromResource(requireContext(), R.array.quizCategoriesEn, android.R.layout.simple_spinner_item)
                btnBackCategory.text = "Start Game"
            }
            "ro"->{
                adapter = ArrayAdapter.createFromResource(requireContext(), R.array.quizCategoriesRo, android.R.layout.simple_spinner_item)
                btnBackCategory.text = "Incepe Jocul"
            }
        }
        return adapter
    }
}