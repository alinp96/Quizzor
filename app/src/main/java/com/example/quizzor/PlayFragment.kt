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
import android.widget.Spinner

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
        val quizCategoryDropDown = view.findViewById<Spinner>(R.id.spnQuizDropDown)
        val btnStartGame = view.findViewById<Button>(R.id.btnStartGame)

        val adapter = setLanguagePreferencesToView(language, view)


        // create adapter for Quiz Category drop down
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        quizCategoryDropDown.adapter = adapter
        // Set as default the first category TODO: Maybe randomize it in the future
        quizCategoryDropDown.setSelection(0)
        // Get the value from adapter
        quizCategoryDropDown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = quizCategoryDropDown.selectedItem as String
                saveDataToSharedPreferences("category", selectedItem)

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case where nothing is selected, if needed
            }
        }

        btnStartGame.setOnClickListener{
            goToScreen("startGame")
        }

        return view
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
        val btnStartGame = view.findViewById<Button>(R.id.btnStartGame)
        var adapter:  ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.quizCategoriesEn, android.R.layout.simple_spinner_item)
        when(language){
            "en"->{
                adapter = ArrayAdapter.createFromResource(requireContext(), R.array.quizCategoriesEn, android.R.layout.simple_spinner_item)
                btnStartGame.text = "Start Game"
            }
            "ro"->{
                adapter = ArrayAdapter.createFromResource(requireContext(), R.array.quizCategoriesRo, android.R.layout.simple_spinner_item)
                btnStartGame.text = "Incepe Jocul"
            }
        }
        return adapter
    }
}