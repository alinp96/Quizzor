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
    private lateinit var tvTitle: TextView
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

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

        tvTitle = view.findViewById<TextView>(R.id.titleTextView)
        backButton = view.findViewById<Button>(R.id.backButton)

        // Initialize shared preferences
        sharedPreferences = requireActivity().getSharedPreferences("userPreferences", Context.MODE_PRIVATE)



        //tvTitle.text = "Settings"
        // Retrieve data from shared preferences
        val language = getDataFromSharedPreferences("language")

        val activity = requireActivity()
        // Set language
        loadSettingsTextLanguage(language, view, activity)

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



        backButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }

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
            changeToNewLanguage(view, activity)
        }

        cbRomanaLanguage.setOnClickListener{
            updateLanguageInSharedPreferencesFromFragment("language", "ro")
            changeToNewLanguage(view, activity)
        }

        cbGermanLanguage.setOnClickListener{
            updateLanguageInSharedPreferencesFromFragment("language", "de")
            changeToNewLanguage(view, activity)
        }

        cbFrancaisLanguage.setOnClickListener{
            updateLanguageInSharedPreferencesFromFragment("language", "fr")
            changeToNewLanguage(view, activity)
        }

        cbHungarianLanguage.setOnClickListener{
            updateLanguageInSharedPreferencesFromFragment("language", "hu")
            changeToNewLanguage(view, activity)
        }

        cbJapanLanguage.setOnClickListener{
            updateLanguageInSharedPreferencesFromFragment("language", "jp")
            changeToNewLanguage(view, activity)
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

    private fun loadSettingsTextLanguage(language: String, view: View, activity: FragmentActivity){
        when(language){
            "en" -> {
                activity.title = "Settings"
                tvTitle.text = "Settings"
                backButton.text = "Back"
            }
            "ro" -> {
                activity.title = "Setări"
                tvTitle.text = "Setări"
                backButton.text = "Înapoi"
            }
            "de" -> {
                activity.title = "Einstellungen"
                tvTitle.text = "Einstellungen"
                backButton.text = "Back"
            }
            "fr" -> {
                activity.title = "Paramètres"
                tvTitle.text = "Paramètres"
                backButton.text = "Retour"
            }
            "hu" -> {
                activity.title = "Beállítások"
                tvTitle.text = "Beállítások"
                backButton.text = "Vissza"
            }
            "jp" -> {
                activity.title = "設定"
                tvTitle.text = "設定"
                backButton.text = "バック"
            }
        }
    }

    private fun changeToNewLanguage(view: View, activity: FragmentActivity){
        val language: String = getDataFromSharedPreferences("language")
        loadSettingsTextLanguage(language, view, activity)
    }
}