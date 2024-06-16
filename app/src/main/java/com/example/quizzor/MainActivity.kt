package com.example.quizzor

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.media.MediaPlayer
import android.media.SoundPool
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
    private lateinit var generalMediaPlayer: MediaPlayer
    private lateinit var specificMediaPlayer: MediaPlayer
    private var isGeneralPlaying = true
    private lateinit var soundPool: SoundPool
    private var soundIdButtonClick: Int = 0
    private var soundIdCorrectAnswer: Int = 0
    private var soundIdWrongAnswer: Int = 0
    private var soundIdQuizCompleted: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userManagement = UserManagement()

        bottomNavigationView.visibility = View.GONE

        sharedPreferences = getSharedPreferences("userPreferences", Context.MODE_PRIVATE)
        val isSoundOn = sharedPreferences.getBoolean("sound", true)

        generalMediaPlayer = MediaPlayer.create(this, R.raw.general_background_music)
        generalMediaPlayer.isLooping = true
        specificMediaPlayer = MediaPlayer.create(this, R.raw.specific_background_music)
        specificMediaPlayer.isLooping = true

        if (isSoundOn) {
            generalMediaPlayer.start()
        }

        // Initialize SoundPool
        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .build()

        // Load sound effects
        soundIdButtonClick = soundPool.load(this, R.raw.button_click, 1)
        soundIdCorrectAnswer = soundPool.load(this, R.raw.correct_answer, 1)
        soundIdWrongAnswer = soundPool.load(this, R.raw.wrong_answer, 1)
        soundIdQuizCompleted = soundPool.load(this, R.raw.quiz_completed, 1)

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
       // sharedPreferences = getSharedPreferences("userPreferences", Context.MODE_PRIVATE)
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

    override fun onDestroy() {
        super.onDestroy()
        generalMediaPlayer.release()
        specificMediaPlayer.release()
    }

    fun toggleBackgroundMusic(isSoundOn: Boolean) {
        if (isSoundOn) {
            saveBoolToSharedPreferences("sound", true)
            if (isGeneralPlaying) {
                generalMediaPlayer.start()
            } else {
                specificMediaPlayer.start()
            }
        } else {
            saveBoolToSharedPreferences("sound", false)
            generalMediaPlayer.pause()
            specificMediaPlayer.pause()
        }
    }

    fun switchToGeneralMusic() {
        if (!isGeneralPlaying) {
            specificMediaPlayer.pause()
            if (sharedPreferences.getBoolean("sound", true)) {
                generalMediaPlayer.start()
            }
            isGeneralPlaying = true
        }
    }

    fun switchToSpecificMusic() {
        if (isGeneralPlaying) {
            generalMediaPlayer.pause()
            if (sharedPreferences.getBoolean("sound", true)) {
                specificMediaPlayer.start()
            }
            isGeneralPlaying = false
        }
    }

    fun playButtonClickSound() {
        if (sharedPreferences.getBoolean("sound", true)) {
            soundPool.play(soundIdButtonClick, 1f, 1f, 1, 0, 1f)
        }
    }

    fun playCorrectAnswerSound() {
        if (sharedPreferences.getBoolean("sound", true)) {
            soundPool.play(soundIdCorrectAnswer, 1f, 1f, 1, 0, 1f)
        }
    }

    fun playWrongAnswerSound() {
        if (sharedPreferences.getBoolean("sound", true)) {
            soundPool.play(soundIdWrongAnswer, 1f, 1f, 1, 0, 1f)
        }
    }

    fun playQuizCompletedSound() {
        if (sharedPreferences.getBoolean("sound", true)) {
            soundPool.play(soundIdQuizCompleted, 1f, 1f, 1, 0, 1f)
        }
    }

    private fun saveDataToSharedPreferences(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun saveBoolToSharedPreferences(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
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