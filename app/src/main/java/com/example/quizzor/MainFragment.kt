package com.example.quizzor

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.w3c.dom.Text

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val btnPlay = view.findViewById<Button>(R.id.btnPlay)
        val btnLeaderboard = view.findViewById<Button>(R.id.btnLeaderboard)
        val btnProfile = view.findViewById<Button>(R.id.btnProfile)
        val btnSettings = view.findViewById<Button>(R.id.btnSettings)
        val test = view.findViewById<TextView>(R.id.textViewWelcomeText)

        sharedPreferences = requireActivity().getSharedPreferences("userPreferences", Context.MODE_PRIVATE)
        //use this if you need the 
        //test.text = getDataFromSharedPreferences("language")

        btnPlay.setOnClickListener{
            goToScreen("play")
        }

        btnLeaderboard.setOnClickListener{
            goToScreen("leaderboard")
        }

        btnProfile.setOnClickListener{
            goToScreen("profile")
        }

        btnSettings.setOnClickListener{
            goToScreen("settings")
        }

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
}