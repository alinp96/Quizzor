package com.example.quizzor

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
import com.google.android.material.bottomnavigation.BottomNavigationView

class AuthenticationFragment : Fragment() {
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
    private lateinit var bottomNavigationView: BottomNavigationView
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
        editTextLoginUsername = view.findViewById<EditText>(R.id.editTextLoginUsername)
        editTextLoginPassword = view.findViewById<EditText>(R.id.editTextLoginPassword)

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
                    }
                }
            }
        }

        return view
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
    }

    private fun hideLoginForm(){
        llAuthenticationButtons.visibility = View.VISIBLE
        llLoginForm.visibility = View.GONE
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

    companion object {
        @JvmStatic
        fun newInstance() =
            AuthenticationFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}