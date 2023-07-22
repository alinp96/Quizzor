package com.example.quizzor

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView

class SettingsFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val btnChangeLanguage = view.findViewById<Button>(R.id.btnChangeLanguage)
        val firstLanguageRowLL = view.findViewById<LinearLayout>(R.id.llLanguageFirstRow)
        val secondLanguageRowLL = view.findViewById<LinearLayout>(R.id.llLanguageSecondRow)
        var counterLanguageBtn: Int = 0

        val cbEnglishLanguage = view.findViewById<CheckBox>(R.id.cbEnglish)
        val cbRomanaLanguage = view.findViewById<CheckBox>(R.id.cbRomana)
        val cbGermanLanguage = view.findViewById<CheckBox>(R.id.cbGerman)
        val cbFrancaisLanguage = view.findViewById<CheckBox>(R.id.cbFrancais)
        val cbHungarianLanguage = view.findViewById<CheckBox>(R.id.cbHungarian)
        val cbJapanLanguage = view.findViewById<CheckBox>(R.id.cbJapan)

        // Initialize shared preferences
        sharedPreferences = requireActivity().getSharedPreferences("userPreferences", Context.MODE_PRIVATE)

        // Retrieve data from shared preferences
        val language = getDataFromSharedPreferences("language")

        val isInitDone = sharedPreferences.getBoolean("isSettingsInitDone", false)

        if (!isInitDone) {
            // Set English as default language
            checkOnlyOneLanguage(language, view)
            val editor = sharedPreferences.edit()
            editor.putBoolean("isSettingsInitDone", true)
            editor.apply()
        }




        btnChangeLanguage.setOnClickListener{
            if (counterLanguageBtn % 2 == 0){
                firstLanguageRowLL.visibility = View.VISIBLE
                secondLanguageRowLL.visibility = View.VISIBLE
            } else{
                firstLanguageRowLL.visibility = View.GONE
                secondLanguageRowLL.visibility = View.GONE
            }
            counterLanguageBtn++
        }

        cbEnglishLanguage.setOnCheckedChangeListener{
                _, isChecked ->
            if (isChecked) {
                checkOnlyOneLanguage("en", view)
            }
        }

        cbRomanaLanguage.setOnCheckedChangeListener{
                _, isChecked ->
            if (isChecked) {
                checkOnlyOneLanguage("ro", view)
            }
        }

        cbGermanLanguage.setOnCheckedChangeListener{
                _, isChecked ->
            if (isChecked) {
                checkOnlyOneLanguage("de", view)
            }
        }

        cbFrancaisLanguage.setOnCheckedChangeListener{
                _, isChecked ->
            if (isChecked) {
                checkOnlyOneLanguage("fr", view)
            }
        }

        cbHungarianLanguage.setOnCheckedChangeListener{
                _, isChecked ->
            if (isChecked) {
                checkOnlyOneLanguage("hu", view)
            }
        }

        cbJapanLanguage.setOnCheckedChangeListener{
                _, isChecked ->
            if (isChecked) {
                checkOnlyOneLanguage("jp", view)
            }
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

    fun checkOnlyOneLanguage(checkedLanguage: String, view: View){
        val cbEnglishLanguage = view.findViewById<CheckBox>(R.id.cbEnglish)
        val cbRomanaLanguage = view.findViewById<CheckBox>(R.id.cbRomana)
        val cbGermanLanguage = view.findViewById<CheckBox>(R.id.cbGerman)
        val cbFrancaisLanguage = view.findViewById<CheckBox>(R.id.cbFrancais)
        val cbHungarianLanguage = view.findViewById<CheckBox>(R.id.cbHungarian)
        val cbJapanLanguage = view.findViewById<CheckBox>(R.id.cbJapan)

        when(checkedLanguage){
            "en"->{
                updateLanguageInSharedPreferencesFromFragment("language", "en")
                cbEnglishLanguage.isChecked = true
                cbEnglishLanguage.isClickable = false
                cbRomanaLanguage.isChecked = false
                cbRomanaLanguage.isClickable = true
                cbGermanLanguage.isChecked = false
                cbGermanLanguage.isClickable = true
                cbFrancaisLanguage.isChecked = false
                cbFrancaisLanguage.isClickable = true
                cbHungarianLanguage.isChecked = false
                cbHungarianLanguage.isClickable = true
                cbJapanLanguage.isChecked = false
                cbJapanLanguage.isClickable = true
            }"ro"->{
                updateLanguageInSharedPreferencesFromFragment("language", "ro")
                cbEnglishLanguage.isChecked = false
                cbEnglishLanguage.isClickable = true
                cbRomanaLanguage.isChecked = true
                cbRomanaLanguage.isClickable = false
                cbGermanLanguage.isChecked = false
                cbGermanLanguage.isClickable = true
                cbFrancaisLanguage.isChecked = false
                cbFrancaisLanguage.isClickable = true
                cbHungarianLanguage.isChecked = false
                cbHungarianLanguage.isClickable = true
                cbJapanLanguage.isChecked = false
                cbJapanLanguage.isClickable = true
            }"de"->{
                updateLanguageInSharedPreferencesFromFragment("language", "de")
                cbEnglishLanguage.isChecked = false
                cbEnglishLanguage.isClickable = true
                cbRomanaLanguage.isChecked = false
                cbRomanaLanguage.isClickable = true
                cbGermanLanguage.isChecked = true
                cbGermanLanguage.isClickable = false
                cbFrancaisLanguage.isChecked = false
                cbFrancaisLanguage.isClickable = true
                cbHungarianLanguage.isChecked = false
                cbHungarianLanguage.isClickable = true
                cbJapanLanguage.isChecked = false
                cbJapanLanguage.isClickable = true
            }"fr"->{
                updateLanguageInSharedPreferencesFromFragment("language", "fr")
                cbEnglishLanguage.isChecked = false
                cbEnglishLanguage.isClickable = true
                cbRomanaLanguage.isChecked = false
                cbRomanaLanguage.isClickable = true
                cbGermanLanguage.isChecked = false
                cbGermanLanguage.isClickable = true
                cbFrancaisLanguage.isChecked = true
                cbFrancaisLanguage.isClickable = false
                cbHungarianLanguage.isChecked = false
                cbHungarianLanguage.isClickable = true
                cbJapanLanguage.isChecked = false
                cbJapanLanguage.isClickable = true
            }"hu"->{
                updateLanguageInSharedPreferencesFromFragment("language", "hu")
                cbEnglishLanguage.isChecked = false
                cbEnglishLanguage.isClickable = true
                cbRomanaLanguage.isChecked = false
                cbRomanaLanguage.isClickable = true
                cbGermanLanguage.isChecked = false
                cbGermanLanguage.isClickable = true
                cbFrancaisLanguage.isChecked = false
                cbFrancaisLanguage.isClickable = true
                cbHungarianLanguage.isChecked = true
                cbHungarianLanguage.isClickable = false
                cbJapanLanguage.isChecked = false
                cbJapanLanguage.isClickable = true
            }"jp"->{
                updateLanguageInSharedPreferencesFromFragment("language", "jp")
                cbEnglishLanguage.isChecked = false
                cbEnglishLanguage.isClickable = true
                cbRomanaLanguage.isChecked = false
                cbRomanaLanguage.isClickable = true
                cbGermanLanguage.isChecked = false
                cbGermanLanguage.isClickable = true
                cbFrancaisLanguage.isChecked = false
                cbFrancaisLanguage.isClickable = true
                cbHungarianLanguage.isChecked = false
                cbHungarianLanguage.isClickable = true
                cbJapanLanguage.isChecked = true
                cbJapanLanguage.isClickable = false
            }
        }
        val textview = view.findViewById<TextView>(R.id.textViewWelcomeText)
        textview.text = getDataFromSharedPreferences("language")
    }

    private fun getDataFromSharedPreferences(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    private fun updateLanguageInSharedPreferencesFromFragment(key:String, newValue: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, newValue)
        editor.apply()
    }
}