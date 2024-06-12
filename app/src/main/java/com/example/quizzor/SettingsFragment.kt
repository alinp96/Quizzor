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

    private lateinit var tvInfoAppName: TextView
    private lateinit var tvInfoDevName: TextView
    private lateinit var tvInfoPurpose: TextView
    private lateinit var tvInfoTitle: TextView
    private lateinit var tvInfoFaculty: TextView
    private lateinit var tvInfoSpecialization: TextView
    private lateinit var tvInfoGroup: TextView

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

        val btnAboutApp = view.findViewById<ImageButton>(R.id.btnAboutApp)
        val informationLL = view.findViewById<LinearLayout>(R.id.llInformation)
        var counterAboutBtn: Int = 0

        tvInfoAppName = view.findViewById<TextView>(R.id.textInfoAppName)
        tvInfoDevName = view.findViewById<TextView>(R.id.textInfoDevName)
        tvInfoPurpose = view.findViewById<TextView>(R.id.textInfoPurpose)
        tvInfoTitle = view.findViewById<TextView>(R.id.textInfoTitle)
        tvInfoFaculty = view.findViewById<TextView>(R.id.textInfoFaculty)
        tvInfoSpecialization = view.findViewById<TextView>(R.id.textInfoSpecialization)
        tvInfoGroup = view.findViewById<TextView>(R.id.textInfoGroup)

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
            informationLL.visibility = View.GONE
            counterAboutBtn = 0
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

        btnAboutApp.setOnClickListener {
            firstLanguageRowLL.visibility = View.GONE
            secondLanguageRowLL.visibility = View.GONE
            counterLanguageBtn = 0
            if (counterAboutBtn % 2 == 0){
                informationLL.visibility = View.VISIBLE
                counterAboutBtn = 1
            } else{
                informationLL.visibility = View.GONE
                counterAboutBtn = 0
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

                tvInfoAppName.text = "Application Name: Quizzor"
                tvInfoDevName.text = "Developer: Alin Popa"
                tvInfoPurpose.text = "Purpose of the app: Developing of general cultur"
                tvInfoTitle.text = "Title: Dissertation"
                tvInfoFaculty.text = "Faculty: Transilvania University"
                tvInfoSpecialization.text = "Specialization: Mobile Applications and Internet Technologies in E-Business"
                tvInfoGroup.text = "Group: MITB xxx"
            }
            "ro" -> {
                activity.title = "Setări"
                tvTitle.text = "Setări"
                backButton.text = "Înapoi"

                tvInfoAppName.text = "Nume aplicație: Quizzor"
                tvInfoDevName.text = "Dezvoltator: Alin Popa"
                tvInfoPurpose.text = "Scopul aplicației: Dezvoltarea culturii generale"
                tvInfoTitle.text = "Titlu: Disertație"
                tvInfoFaculty.text = "Facultate: Universitatea Transilvania"
                tvInfoSpecialization.text = "Specializare: Aplicații Mobile și Tehnologii Internet în E-Business"
                tvInfoGroup.text = "Grupa: MITB xxx"

            }
            "de" -> {
                activity.title = "Einstellungen"
                tvTitle.text = "Einstellungen"
                backButton.text = "Back"

                tvInfoAppName.text = "Anwendungsname: Quizzor"
                tvInfoDevName.text = "Entwickler: Alin Popa"
                tvInfoPurpose.text = "Zweck der App: Entwicklung der Allgemeinbildung"
                tvInfoTitle.text = "Titel: Dissertation"
                tvInfoFaculty.text = "Fakultät: Transilvania Universität"
                tvInfoSpecialization.text = "Spezialisierung: Mobile Anwendungen und Internettechnologien im E-Business"
                tvInfoGroup.text = "Gruppe: MITB xxx"

            }
            "fr" -> {
                activity.title = "Paramètres"
                tvTitle.text = "Paramètres"
                backButton.text = "Retour"

                tvInfoAppName.text = "Nom de l'application: Quizzor"
                tvInfoDevName.text = "Développeur: Alin Popa"
                tvInfoPurpose.text = "But de l'application: Développement de la culture générale"
                tvInfoTitle.text = "Titre: Dissertation"
                tvInfoFaculty.text = "Faculté: Université de Transilvania"
                tvInfoSpecialization.text = "Spécialisation: Applications Mobiles et Technologies Internet dans l'E-Business"
                tvInfoGroup.text = "Groupe: MITB xxx"

            }
            "hu" -> {
                activity.title = "Beállítások"
                tvTitle.text = "Beállítások"
                backButton.text = "Vissza"

                tvInfoAppName.text = "Alkalmazás neve: Quizzor"
                tvInfoDevName.text = "Fejlesztő: Alin Popa"
                tvInfoPurpose.text = "Az alkalmazás célja: Az általános műveltség fejlesztése"
                tvInfoTitle.text = "Cím: Disszertáció"
                tvInfoFaculty.text = "Kar: Transilvania Egyetem"
                tvInfoSpecialization.text = "Szakirány: Mobilalkalmazások és Internetes Technológiák az E-Businessben"
                tvInfoGroup.text = "Csoport: MITB xxx"

            }
            "jp" -> {
                activity.title = "設定"
                tvTitle.text = "設定"
                backButton.text = "バック"

                tvInfoAppName.text = "アプリ名: Quizzor"
                tvInfoDevName.text = "開発者: Alin Popa"
                tvInfoPurpose.text = "アプリの目的: 一般教養の発展"
                tvInfoTitle.text = "タイトル: 論文"
                tvInfoFaculty.text = "学部: トランシルバニア大学"
                tvInfoSpecialization.text = "専門分野: Eビジネスにおけるモバイルアプリケーションとインターネット技術"
                tvInfoGroup.text = "グループ: MITB xxx"

            }
        }
    }

    private fun changeToNewLanguage(view: View, activity: FragmentActivity){
        val language: String = getDataFromSharedPreferences("language")
        loadSettingsTextLanguage(language, view, activity)
    }
}