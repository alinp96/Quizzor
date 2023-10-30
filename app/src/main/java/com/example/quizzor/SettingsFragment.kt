package com.example.quizzor

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity

class SettingsFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var titleTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val backButton = view.findViewById<Button>(R.id.backButton)
        titleTextView = view.findViewById<TextView>(R.id.titleTextView)

        val btnChangeLanguage = view.findViewById<ImageButton>(R.id.btnChangeLanguage)
        val firstLanguageRowLL = view.findViewById<LinearLayout>(R.id.llLanguageFirstRow)
        val secondLanguageRowLL = view.findViewById<LinearLayout>(R.id.llLanguageSecondRow)
        val llRowOfSettingsButtons = view.findViewById<LinearLayout>(R.id.llRowOfSettingsButtons)
        var counterLanguageBtn: Int = 0

        val cbEnglishLanguage = view.findViewById<ImageButton>(R.id.btnEnglish)
        val cbRomanaLanguage = view.findViewById<ImageButton>(R.id.btnRomana)
        val cbGermanLanguage = view.findViewById<ImageButton>(R.id.btnGerman)
        val cbFrancaisLanguage = view.findViewById<ImageButton>(R.id.btnFrancais)
        val cbHungarianLanguage = view.findViewById<ImageButton>(R.id.btnHungarian)
        val cbJapanLanguage = view.findViewById<ImageButton>(R.id.btnJapan)

        backButton.visibility = View.GONE
        // Initialize shared preferences
        sharedPreferences = requireActivity().getSharedPreferences("userPreferences", Context.MODE_PRIVATE)

        // Retrieve data from shared preferences
        val language = getDataFromSharedPreferences("language")

        val activity = requireActivity()
        // Set language
        loadSettingsTextLanguage(language)

        val isInitDone = sharedPreferences.getBoolean("isSettingsInitDone", false)

        if (!isInitDone) {
            // Set English as default language
            updateLanguageInSharedPreferencesFromFragment("language", "en")
            val editor = sharedPreferences.edit()
            editor.putBoolean("isSettingsInitDone", true)
            editor.apply()
        }
        firstLanguageRowLL.visibility = View.GONE
        secondLanguageRowLL.visibility = View.GONE



        btnChangeLanguage.setOnClickListener{
            if (counterLanguageBtn % 2 == 0){
                firstLanguageRowLL.visibility = View.VISIBLE
                secondLanguageRowLL.visibility = View.VISIBLE
                counterLanguageBtn = 1
            } else{
                firstLanguageRowLL.visibility = View.GONE
                secondLanguageRowLL.visibility = View.GONE
                counterLanguageBtn = 0
            }
        }

        cbEnglishLanguage.setOnClickListener{
            updateLanguageInSharedPreferencesFromFragment("language", "en")
            changeToNewLanguage()
        }

        cbRomanaLanguage.setOnClickListener{
            updateLanguageInSharedPreferencesFromFragment("language", "ro")
            changeToNewLanguage()
        }

        cbGermanLanguage.setOnClickListener{
            updateLanguageInSharedPreferencesFromFragment("language", "de")
            changeToNewLanguage()
        }

        cbFrancaisLanguage.setOnClickListener{
            updateLanguageInSharedPreferencesFromFragment("language", "fr")
            changeToNewLanguage()
        }

        cbHungarianLanguage.setOnClickListener{
            updateLanguageInSharedPreferencesFromFragment("language", "hu")
            changeToNewLanguage()
        }

        cbJapanLanguage.setOnClickListener{
            updateLanguageInSharedPreferencesFromFragment("language", "jp")
            changeToNewLanguage()
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            SettingsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private fun getDataFromSharedPreferences(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    private fun updateLanguageInSharedPreferencesFromFragment(key:String, newValue: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, newValue)
        editor.apply()
    }

    private fun loadSettingsTextLanguage(language: String){
        when(language){
            "en" -> {
                titleTextView.text = "Settings"
            }
            "ro" -> {
                titleTextView.text = "Setări"
            }
            "de" -> {
                titleTextView.text = "Einstellungen"
            }
            "fr" -> {
                titleTextView.text = "Paramètres"
            }
            "hu" -> {
                titleTextView.text = "Beállítások"
            }
            "jp" -> {
                titleTextView.text = "設定"
            }
        }
    }

    private fun changeToNewLanguage(){
        val language: String = getDataFromSharedPreferences("language")
        loadSettingsTextLanguage(language)
    }
}