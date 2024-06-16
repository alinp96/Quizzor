package com.example.quizzor

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var txtWelcome: TextView
    //private lateinit var tvTitle: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        (activity as MainActivity).switchToGeneralMusic()
        var backButton: Button = view.findViewById<Button>(R.id.backButton)
        val btnPlay = view.findViewById<ImageButton>(R.id.btnPlay)
        txtWelcome = view.findViewById<TextView>(R.id.textViewWelcomeText)


        val userManager = UserManagement()
        //tvTitle = view.findViewById<TextView>(R.id.titleTextView)
        //val btnLeaderboard = view.findViewById<Button>(R.id.btnLeaderboard)
        //val btnProfile = view.findViewById<Button>(R.id.btnProfile)
        //val btnSettings = view.findViewById<ImageButton>(R.id.btnSettings)
        //val test = view.findViewById<TextView>(R.id.textViewWelcomeText)
        var language: String = ""

        val activity = requireActivity()
        activity.title = "Quizzor"


        sharedPreferences = requireActivity().getSharedPreferences("userPreferences", Context.MODE_PRIVATE)

        language = getDataFromSharedPreferences("language")
        setLanguagePreferencesToView(language, view)

        btnPlay.setOnClickListener{
            goToScreen("play")
        }

        backButton.visibility = View.GONE

        // Inflate the layout for this fragment
        return view
    }

    private fun goToScreen(fragment: String) {
        when(fragment){
            "play" ->{
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, PlayFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
            }
            "leaderboard" ->{
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, LeaderboardFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
            }
            "profile" ->{
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, ProfileFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
            }
            "settings" ->{
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
            }
        }


    }
    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private fun getDataFromSharedPreferences(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    private fun setLanguagePreferencesToView(language: String, view: View){
        when(language){
            "en"->{
                txtWelcome.text = "Welcome to Quizzor!"
            }
            "ro"->{
                txtWelcome.text = "Bine ați venit la Quizzor!"
            }
            "de" -> {
                txtWelcome.text = "Willkommen bei Quizzor!"
            }
            "fr" -> {
                txtWelcome.text = "Bienvenue sur Quizzor!"
            }
            "hu" -> {
                txtWelcome.text = "Üdvözöljük a Quizzorban!"
            }
            "jp" -> {
                txtWelcome.text = "Quizzorへようこそ！"
            }
        }
    }
}