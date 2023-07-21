package com.example.quizzor

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.ConfigurationCompat
import org.intellij.lang.annotations.Language
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get default language from the system
        val defaultLanguage = Locale.getDefault().language

        // Initialize shared preferences
        sharedPreferences = getSharedPreferences("userPreferences", Context.MODE_PRIVATE)

        // Save data to shared preferences
        saveDataToSharedPreferences("language", defaultLanguage)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
    private fun saveDataToSharedPreferences(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }
}