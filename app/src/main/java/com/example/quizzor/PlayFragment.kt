package com.example.quizzor

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.style.TabStopSpan.Standard
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import org.w3c.dom.Text

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
    private lateinit var btnGeneralKnowledge: View
    private lateinit var btnBackCategory: View
    private lateinit var btnTrivia: View

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
        llGeneralKnowledgeSubs= view.findViewById<LinearLayout>(R.id.llRowOfGeneralKnowledgeSubs)

        btnBackCategory = view.findViewById<LinearLayout>(R.id.btnBackCategory)
        btnGeneralKnowledge = view.findViewById<Button>(R.id.btnGeneralKnowledge)
        btnTrivia = view.findViewById<Button>(R.id.btnTrivia)

        //val adapter = setLanguagePreferencesToView(language, view)


        // Show categories
        showCategories()

        btnGeneralKnowledge.setOnClickListener{
            hideEverything()
            txtViewSubCategories.visibility = View.VISIBLE
            llGeneralKnowledgeSubs.visibility = View.VISIBLE
            llBackCategory.visibility = View.VISIBLE
        }

        btnBackCategory.setOnClickListener{
            hideEverything()
            showCategories()
        }

        btnTrivia.setOnClickListener{
            saveDataToSharedPreferences("category", "Trivia")
            goToScreen("startGame")
        }

        // THIS TO THE END OF EACH BUTTON
        /*btnStartGame.setOnClickListener{
            goToScreen("startGame")
        }*/

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
        llBackCategory.visibility = View.GONE
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