package com.example.quizzor

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

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
    private lateinit var txtViewCategories: View
    private lateinit var txtViewSubCategories: View
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

    private lateinit var btnGeneralKnowledge: View
    private lateinit var btnScienceAndTechnology: View
    private lateinit var btnHistoryAndGeography: View
    private lateinit var btnEntertainment: View
    private lateinit var btnSports: View
    private lateinit var btnFoodAndDrinks: View

    private lateinit var btnBackCategory: View
    private lateinit var btnTrivia: View
    private lateinit var btnWorldFacts: View
    private lateinit var btnFamousPersonalities: View
    private lateinit var btnPhysics: View
    private lateinit var btnChemistry: View
    private lateinit var btnBiology: View
    private lateinit var btnComputerScience: View
    private lateinit var btnContriesAndCapitals: View
    private lateinit var btnGeographicFactsAndTrivia: View
    private lateinit var btnFamousHistoricalFigures: View
    private lateinit var btnHistoricalEvents: View
    private lateinit var btnMovies: View
    private lateinit var btnTvShows: View
    private lateinit var btnMusic: View
    private lateinit var btnBooksAndLiterature: View
    private lateinit var btnCelebrityAndPopCulture: View
    private lateinit var btnVideoGames: View
    private lateinit var btnSoccer: View
    private lateinit var btnUnusualSportsFacts: View
    private lateinit var btnMemorableSportingEvents: View
    private lateinit var btnCuisine: View
    private lateinit var btnIngredients: View
    private lateinit var btnBeverages: View
    private lateinit var btnFoodAndTrivia: View

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
        txtViewCategories = view.findViewById<TextView>(R.id.textViewChooseCategory)
        txtViewSubCategories = view.findViewById<TextView>(R.id.textViewChooseSubCategory)
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

        btnBackCategory = view.findViewById<LinearLayout>(R.id.btnBackCategory)
        btnGeneralKnowledge = view.findViewById<Button>(R.id.btnGeneralKnowledge)
        btnTrivia = view.findViewById<Button>(R.id.btnTrivia)
        btnWorldFacts = view.findViewById<Button>(R.id.btnWorldFacts)
        btnFamousPersonalities = view.findViewById<Button>(R.id.btnFamousPersonalities)
        btnScienceAndTechnology = view.findViewById<Button>(R.id.btnScienceAndTechnology)
        btnPhysics = view.findViewById<Button>(R.id.btnPhysics)
        btnChemistry = view.findViewById<Button>(R.id.btnChemistry)
        btnBiology = view.findViewById<Button>(R.id.btnBiology)
        btnComputerScience = view.findViewById<Button>(R.id.btnComputerScience)
        btnHistoryAndGeography = view.findViewById<Button>(R.id.btnHistoryAndGeography)
        btnEntertainment = view.findViewById<Button>(R.id.btnEntertainment)
        btnSports = view.findViewById<Button>(R.id.btnSports)
        btnFoodAndDrinks = view.findViewById<Button>(R.id.btnFoodAndDrinks)
        btnContriesAndCapitals = view.findViewById<Button>(R.id.btnContriesAndCapitals)
        btnGeographicFactsAndTrivia = view.findViewById<Button>(R.id.btnGeographicFactsAndTrivia)
        btnFamousHistoricalFigures = view.findViewById<Button>(R.id.btnFamousHistoricalFigures)
        btnHistoricalEvents = view.findViewById<Button>(R.id.btnHistoricalEvents)
        btnMovies = view.findViewById<Button>(R.id.btnMovies)
        btnTvShows = view.findViewById<Button>(R.id.btnTvShows)
        btnMusic = view.findViewById<Button>(R.id.btnMusic)
        btnBooksAndLiterature = view.findViewById<Button>(R.id.btnBooksAndLiterature)
        btnCelebrityAndPopCulture = view.findViewById<Button>(R.id.btnCelebrityAndPopCulture)
        btnVideoGames = view.findViewById<Button>(R.id.btnVideoGames)
        btnSoccer = view.findViewById<Button>(R.id.btnSoccer)
        btnUnusualSportsFacts = view.findViewById<Button>(R.id.btnUnusualSportsFacts)
        btnMemorableSportingEvents = view.findViewById<Button>(R.id.btnMemorableSportingEvents)
        btnCuisine = view.findViewById<Button>(R.id.btnCuisine)
        btnIngredients = view.findViewById<Button>(R.id.btnIngredients)
        btnBeverages = view.findViewById<Button>(R.id.btnBeverages)
        btnFoodAndTrivia = view.findViewById<Button>(R.id.btnFoodAndTrivia)

        //val adapter = setLanguagePreferencesToView(language, view)


        // Show categories
        hideEverything()
        showCategories()

        btnGeneralKnowledge.setOnClickListener{
            hideEverything()
            txtViewSubCategories.visibility = View.VISIBLE
            llGeneralKnowledgeSubs.visibility = View.VISIBLE
            llBackCategory.visibility = View.VISIBLE
        }

        btnTrivia.setOnClickListener{
            saveDataToSharedPreferences("category", "Trivia")
            goToScreen("startGame")
        }

        btnWorldFacts.setOnClickListener{
            saveDataToSharedPreferences("category", "WorldFacts")
            goToScreen("startGame")
        }

        btnFamousPersonalities.setOnClickListener{
            saveDataToSharedPreferences("category", "FamousPersonalities")
            goToScreen("startGame")
        }

        btnScienceAndTechnology.setOnClickListener{
            hideEverything()
            txtViewSubCategories.visibility = View.VISIBLE
            ll1stRowOfScienceAndTechnologySubs.visibility = View.VISIBLE
            ll2ndRowOfScienceAndTechnologySubs.visibility = View.VISIBLE
            llBackCategory.visibility = View.VISIBLE
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
            saveDataToSharedPreferences("category", "ComputerScience")
            goToScreen("startGame")
        }

        btnHistoryAndGeography.setOnClickListener{
            hideEverything()
            txtViewSubCategories.visibility = View.VISIBLE
            ll1stRowOfHistoryAndGeographySubs.visibility = View.VISIBLE
            ll2ndRowOfHistoryAndGeographySubs.visibility = View.VISIBLE
            llBackCategory.visibility = View.VISIBLE
        }

        btnContriesAndCapitals.setOnClickListener{
            saveDataToSharedPreferences("category", "ContiresAndCaptitals")
            goToScreen("startGame")
        }

        btnGeographicFactsAndTrivia.setOnClickListener{
            saveDataToSharedPreferences("category", "GeographicFactsAndTrivia")
            goToScreen("startGame")
        }

        btnFamousHistoricalFigures.setOnClickListener{
            saveDataToSharedPreferences("category", "FamousHistoricalFigures")
            goToScreen("startGame")
        }

        btnHistoricalEvents.setOnClickListener{
            saveDataToSharedPreferences("category", "HistoricalEvents")
            goToScreen("startGame")
        }

        btnEntertainment.setOnClickListener{
            hideEverything()
            txtViewSubCategories.visibility = View.VISIBLE
            ll1stRowOfEntertainmentSubs.visibility = View.VISIBLE
            ll2ndRowOfEntertainmentSubs.visibility = View.VISIBLE
            llBackCategory.visibility = View.VISIBLE
        }

        btnMovies.setOnClickListener{
            saveDataToSharedPreferences("category", "Movies")
            goToScreen("startGame")
        }

        btnTvShows.setOnClickListener{
            saveDataToSharedPreferences("category", "TvShows")
            goToScreen("startGame")
        }

        btnMusic.setOnClickListener{
            saveDataToSharedPreferences("category", "Music")
            goToScreen("startGame")
        }

        btnBooksAndLiterature.setOnClickListener{
            saveDataToSharedPreferences("category", "Books And Literature")
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
            hideEverything()
            txtViewSubCategories.visibility = View.VISIBLE
            llRowOfSportsSubs.visibility = View.VISIBLE
            llBackCategory.visibility = View.VISIBLE
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
            hideEverything()
            txtViewSubCategories.visibility = View.VISIBLE
            ll1stRowOfFoodAndDrinksSubs.visibility = View.VISIBLE
            ll2ndRowOfFoodAndDrinksSubs.visibility = View.VISIBLE
            llBackCategory.visibility = View.VISIBLE
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

        // THIS TO THE END OF EACH BUTTON
            /*goToScreen("startGame")*/

        return view
    }

    private fun showCategories() {
        txtViewCategories.visibility = View.VISIBLE
        ll1stRowOfCategories.visibility = View.VISIBLE
        ll2ndRowOfCategories.visibility = View.VISIBLE
    }

    private fun hideEverything() {
        txtViewCategories.visibility = View.GONE
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