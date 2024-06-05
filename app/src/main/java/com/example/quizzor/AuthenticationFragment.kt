package com.example.quizzor

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthenticationFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var llAuthenticationButtons: LinearLayout
    private lateinit var llRegisterForm: LinearLayout
    private lateinit var llLoginForm: LinearLayout
    private lateinit var imgBtnRegister: ImageButton
    private lateinit var imgBtnLogin: ImageButton
    private lateinit var buttonRegisterBack: Button
    private lateinit var buttonLoginBack: Button
    private lateinit var buttonRegister: Button
    private lateinit var buttonLogin: Button
    private lateinit var editTextLoginUsername: EditText
    private lateinit var editTextLoginPassword: EditText
    private lateinit var textViewLoginErrorMsg: TextView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var editTextRegisterNickname: EditText
    private lateinit var editTextRegisterUsername: EditText
    private lateinit var editTextRegisterPassword: EditText
    private lateinit var editTextRegisterConfirmPassword: EditText
    private lateinit var textViewRegisterErrorMsg: TextView
    private lateinit var textViewRegisterTitle: TextView
    private lateinit var textViewLoginTitle: TextView
    private lateinit var language: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_authentication, container, false)
        val activity = activity as? MainActivity
        val userManager: UserManagement = activity?.getUserManagement() ?: throw IllegalStateException("MainActivity expected")
        sharedPreferences = requireActivity().getSharedPreferences("userPreferences", Context.MODE_PRIVATE)
        language = getDataFromSharedPreferences("language")
        bottomNavigationView = activity.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        llAuthenticationButtons = view.findViewById<LinearLayout>(R.id.llAuthenticationButtons)
        llRegisterForm = view.findViewById<LinearLayout>(R.id.llRegisterForm)
        llLoginForm = view.findViewById<LinearLayout>(R.id.llLoginForm)
        imgBtnRegister = view.findViewById<ImageButton>(R.id.imgBtnRegister)
        imgBtnLogin = view.findViewById<ImageButton>(R.id.imgBtnLogin)
        buttonRegisterBack = view.findViewById<Button>(R.id.buttonRegisterBack)
        buttonLoginBack = view.findViewById<Button>(R.id.buttonLoginBack)
        buttonRegister = view.findViewById<Button>(R.id.buttonRegister)
        buttonLogin = view.findViewById<Button>(R.id.buttonLogin)
        textViewLoginErrorMsg = view.findViewById<TextView>(R.id.textViewLoginErrorMsg)
        editTextLoginUsername = view.findViewById<EditText>(R.id.editTextLoginUsername)
        editTextLoginPassword = view.findViewById<EditText>(R.id.editTextLoginPassword)
        editTextRegisterNickname = view.findViewById<EditText>(R.id.editTextRegisterNickname)
        editTextRegisterUsername = view.findViewById<EditText>(R.id.editTextRegisterUsername)
        editTextRegisterPassword = view.findViewById<EditText>(R.id.editTextRegisterPassword)
        editTextRegisterConfirmPassword = view.findViewById<EditText>(R.id.editTextRegisterConfirmPassword)
        textViewRegisterErrorMsg = view.findViewById<TextView>(R.id.textViewRegisterErrorMsg)
        textViewRegisterTitle = view.findViewById<TextView>(R.id.textViewRegisterTitle)
        textViewLoginTitle = view.findViewById<TextView>(R.id.textViewLoginTitle)

        loadUIText(language)

        imgBtnRegister.setOnClickListener{
            showRegisterForm()
        }

        imgBtnLogin.setOnClickListener{
            showLoginForm()
        }

        buttonRegisterBack.setOnClickListener{
            hideRegisterForm()
        }

        buttonLoginBack.setOnClickListener {
            hideLoginForm()
        }

        buttonLogin.setOnClickListener {
            if (editTextLoginUsername.text.toString() != "" && editTextLoginPassword.text.toString() != ""){
                userManager.signIn(editTextLoginUsername.text.toString(), editTextLoginPassword.text.toString()) { callback, test ->
                    if (test != null) {
                        loginSuccessfully()
                    } else{
                        loginFailed()
                    }
                }
            } else {
                emptyFieldsWarning()
            }
        }

        buttonRegister.setOnClickListener {
            if (registerFormFilled()){
                if(registerPasswordCheck()){
                    registerUser(userManager, editTextRegisterNickname.text.toString(), editTextRegisterUsername.text.toString(),editTextRegisterPassword.text.toString())
                }
            }
        }

        return view
    }

    private fun getDataFromSharedPreferences(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }
    private fun showRegisterForm(){
        llAuthenticationButtons.visibility = View.GONE
        llRegisterForm.visibility = View.VISIBLE
    }

    private fun showLoginForm(){
        llAuthenticationButtons.visibility = View.GONE
        llLoginForm.visibility = View.VISIBLE
    }

    private fun hideRegisterForm(){
        llAuthenticationButtons.visibility = View.VISIBLE
        llRegisterForm.visibility = View.GONE
        textViewRegisterErrorMsg.visibility = View.GONE
        editTextRegisterNickname.text.clear()
        editTextRegisterUsername.text.clear()
        editTextRegisterPassword.text.clear()
        editTextRegisterConfirmPassword.text.clear()
    }

    private fun hideLoginForm(){
        llAuthenticationButtons.visibility = View.VISIBLE
        llLoginForm.visibility = View.GONE
        textViewLoginErrorMsg.visibility = View.GONE
        editTextLoginUsername.text.clear()
        editTextLoginPassword.text.clear()
    }

    private fun hideRegisterButtons(){
        llAuthenticationButtons.visibility = View.GONE
    }

    private fun loginSuccessfully(){
        hideLoginForm()
        hideRegisterButtons()

        bottomNavigationView.visibility = View.VISIBLE
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    private fun loginFailed(){
        textViewLoginErrorMsg.text = translatedErrorMessage(language, "IncorrectCredentials")
            textViewLoginErrorMsg.visibility = View.VISIBLE
    }

    private fun emptyFieldsWarning(){
        textViewLoginErrorMsg.text = translatedErrorMessage(language, "FormNotFilled")
        textViewLoginErrorMsg.visibility = View.VISIBLE
    }

    private fun registerFormFilled(): Boolean{
        if (editTextRegisterNickname.text.toString() != ""
            && editTextRegisterUsername.text.toString() != ""
            && editTextRegisterPassword.text.toString() != ""
            && editTextRegisterConfirmPassword.text.toString() != ""){
            return true
        }
        textViewRegisterErrorMsg.text = translatedErrorMessage(language, "FormNotFilled")
        textViewRegisterErrorMsg.visibility = View.VISIBLE
        return false
    }

    private fun registerPasswordCheck(): Boolean{
        if (editTextRegisterPassword.text.toString() == editTextRegisterConfirmPassword.text.toString()){
            return true
        }
        textViewRegisterErrorMsg.text = translatedErrorMessage(language, "DifferentPassword")
        textViewRegisterErrorMsg.visibility = View.VISIBLE
        return false
    }

    private fun registerUserError(type: String){
        if (type == "nickname"){
            textViewRegisterErrorMsg.text = translatedErrorMessage(language, "NicknameCheck")
            textViewRegisterErrorMsg.visibility = View.VISIBLE
        }else if (type == "user"){
            textViewRegisterErrorMsg.text = translatedErrorMessage(language, "UsernameCheck")
            textViewRegisterErrorMsg.visibility = View.VISIBLE
        }

    }

    private fun registerUser(userManager: UserManagement, nickname: String, user: String, password: String){
        CoroutineScope(Dispatchers.Main).launch {
            var registerCompleted: Char = userManager.registerUser(nickname, user, password)
            handleRegisterResult(registerCompleted)
        }
    }

    private fun handleRegisterResult(result: Char){
        when(result){
            '1' -> resetFragmentView()
            'n' -> registerUserError("nickname")
            'u' -> registerUserError("user")
        }
    }

    private fun resetFragmentView(){
        textViewRegisterErrorMsg.visibility = View.GONE
        llRegisterForm.visibility = View.GONE
        llAuthenticationButtons.visibility = View.VISIBLE
    }

    private fun loadUIText(language: String){
        when (language){
            "en" -> {
                textViewRegisterTitle.text = "Register"
                editTextRegisterNickname.hint = "Nickname"
                editTextRegisterUsername.hint = "Username"
                editTextRegisterPassword.hint = "Password"
                editTextRegisterConfirmPassword.hint = "Confirm Password"
                buttonRegisterBack.text = "Back"
                buttonRegister.text = "Register"

                textViewLoginTitle.text = "Login"
                editTextLoginUsername.hint = "Username"
                editTextLoginPassword.hint = "Password"
                buttonLogin.text = "Login"
            }
            "ro" -> {
                textViewRegisterTitle.text = "Inregistrare"
                editTextRegisterNickname.hint = "Porecla"
                editTextRegisterUsername.hint = "Nume utilizator"
                editTextRegisterPassword.hint = "Parola"
                editTextRegisterConfirmPassword.hint = "Confirmarea parolei"
                buttonRegisterBack.text = "Inapoi"
                buttonRegister.text = "Inregistrare"

                textViewLoginTitle.text = "Autentificare"
                editTextLoginUsername.hint = "Nume Utilizator"
                editTextLoginPassword.hint = "Parola"
                buttonLogin.text = "Inregistrare"
            }
        }
    }

    private fun translatedErrorMessage(language: String, error: String): String{
        var errorMsg: String = ""
        when(language){
            "en" -> {
                when(error){
                    "IncorrectCredentials" -> {
                        errorMsg = "Incorrect Credentials!"
                    }
                    "FormNotFilled" -> {
                        errorMsg = "Please complete all form fields!"
                    }
                    "DifferentPassword" -> {
                        errorMsg = "Password fields are different!"
                    }
                    "NicknameCheck" -> {
                        errorMsg = "Nickname already exists! Choose another one!"
                    }
                    "UsernameCheck" -> {
                        errorMsg = "Username already exists! Choose another one!"
                    }
                }
            }
            "ro" -> {
                when(error){
                    "IncorrectCredentials" -> {
                        errorMsg = "Credintiale incorecte!"
                    }
                    "FormNotFilled" -> {
                        errorMsg = "Te rog completeaza toate celulele!"
                    }
                    "DifferentPassword" -> {
                        errorMsg = "Campurile de parole sunt diferite!"
                    }
                    "NicknameCheck" -> {
                        errorMsg = "Porecla deja exista! Alege alta porleca!"
                    }
                    "UsernameCheck" -> {
                        errorMsg = "Numele de utilizator deja exista! Alege altul!"
                    }
                }
            }
        }
        return errorMsg
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AuthenticationFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}