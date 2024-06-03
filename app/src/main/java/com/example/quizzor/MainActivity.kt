package com.example.quizzor

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.os.ConfigurationCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.auth.User
import org.intellij.lang.annotations.Language
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userManagement: UserManagement
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userManagement = UserManagement()

        bottomNavigationView.visibility = View.GONE
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.mainFragment -> {
                    // Replace the current fragment with the first fragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MainFragment())
                        .commit()
                    return@setOnItemSelectedListener true
                }
                R.id.LeaderboardFragment -> {
                    // Replace the current fragment with the first fragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, LeaderboardFragment())
                        .commit()
                    return@setOnItemSelectedListener true
                }
                R.id.ProfileFragment -> {
                    // Replace the current fragment with the first fragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ProfileFragment())
                        .commit()
                    return@setOnItemSelectedListener true
                }
                R.id.settingsFragment -> {
                    // Replace the current fragment with the second fragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SettingsFragment())
                        .commit()
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener false
            }
        }

        // Initialize shared preferences
        sharedPreferences = getSharedPreferences("userPreferences", Context.MODE_PRIVATE)
        val isInitDone = sharedPreferences.getBoolean("isInitDone", false)

        if (!isInitDone) {
            init()
            val editor = sharedPreferences.edit()
            editor.putBoolean("isInitDone", true)
            editor.apply()
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AuthenticationFragment.newInstance())
                .commitNow()
        }
    }
    private fun saveDataToSharedPreferences(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    // This function will run only once, on the first start of the app
    private fun init(){
        // Get default language from the system
        val defaultLanguage = Locale.getDefault().language

        // Save data to shared preferences
        saveDataToSharedPreferences("language", defaultLanguage)
    }

    private val bottomNavigationView: BottomNavigationView by lazy {
        findViewById(R.id.bottom_navigation)
    }

    public fun getUserManagement(): UserManagement{
        return userManagement
    }
}