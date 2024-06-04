package com.example.quizzor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {
    private lateinit var tvTitle: TextView
    private lateinit var backButton: Button
    private lateinit var userManager: UserManagement

    private lateinit var llProfileMainView: LinearLayout
    private lateinit var tvPrincipalText: TextView
    private lateinit var tvSecondaryText: TextView
    private lateinit var btnChangeNickname: Button
    private lateinit var btnChangePassword: Button
    private lateinit var btnResetScore: Button

    private lateinit var llChangeNickname: LinearLayout
    private lateinit var tvCurrentNickname: TextView
    private lateinit var etChangeNickname: EditText
    private lateinit var btnResetNickname: Button

    private lateinit var llChangePassword: LinearLayout
    private lateinit var tvPasswordText: TextView
    private lateinit var etCurrentPassword: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var etConfirmNewPassword: EditText
    private lateinit var btnResetPassword: Button
    private lateinit var tvErrorNickname: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val activity = activity as? MainActivity
        userManager = activity?.getUserManagement() ?: throw IllegalStateException("MainActivity expected")

        llProfileMainView = view.findViewById<LinearLayout>(R.id.llProfileMainView)
        tvPrincipalText = view.findViewById<TextView>(R.id.tvPrincipalText)
        tvSecondaryText = view.findViewById<TextView>(R.id.tvSecondaryText)
        tvTitle = view.findViewById<TextView>(R.id.titleTextView)
        btnChangeNickname = view.findViewById<Button>(R.id.btnChangeNickname)
        btnChangePassword = view.findViewById<Button>(R.id.btnChangePassword)
        btnResetScore = view.findViewById<Button>(R.id.btnResetScore)
        llChangeNickname = view.findViewById<LinearLayout>(R.id.llChangeNickname)
        tvCurrentNickname = view.findViewById<TextView>(R.id.tvCurrentNickname)
        etChangeNickname = view.findViewById<EditText>(R.id.etChangeNickname)
        btnResetNickname = view.findViewById<Button>(R.id.btnResetNickname)
        llChangePassword = view.findViewById<LinearLayout>(R.id.llChangePassword)
        tvPasswordText = view.findViewById<TextView>(R.id.tvPasswordText)
        etCurrentPassword = view.findViewById<EditText>(R.id.etCurrentPassword)
        etNewPassword = view.findViewById<EditText>(R.id.etNewPassword)
        etConfirmNewPassword = view.findViewById<EditText>(R.id.etConfirmNewPassword)
        btnResetPassword = view.findViewById<Button>(R.id.btnResetPassword)
        tvErrorNickname = view.findViewById<TextView>(R.id.tvErrorNickname)
        backButton = view.findViewById<Button>(R.id.backButton)

        tvTitle.text = "Profile"
        setScore()
        setNickname()


        /*backButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }*/

        backButton.setOnClickListener {
            showMainProfilePage()
        }

        btnChangeNickname.setOnClickListener {
            showChangeNicknameForm()
        }

        btnChangePassword.setOnClickListener {
            showChangePasswordForm()
        }

        btnResetScore.setOnClickListener {
            resetScore()
        }

        btnResetNickname.setOnClickListener {
            resetNickname()
        }

        return view
    }

    private fun showMainProfilePage(){
        llProfileMainView.visibility = View.VISIBLE
        llChangeNickname.visibility = View.GONE
        llChangePassword.visibility = View.GONE
    }

    private fun showChangeNicknameForm(){
        llProfileMainView.visibility = View.GONE
        llChangeNickname.visibility = View.VISIBLE
        llChangePassword.visibility = View.GONE
    }

    private fun showChangePasswordForm(){
        llProfileMainView.visibility = View.GONE
        llChangeNickname.visibility = View.GONE
        llChangePassword.visibility = View.VISIBLE
    }

    private fun resetScore(){
        val db = FirebaseFirestore.getInstance()
        val docId = userManager.getLoggedInUserId().toString()
        val docRef = db.collection("users").document(docId)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val currentScoreMap = documentSnapshot.get("score") as? Map<String, Any> ?: emptyMap()
            val updatedScoreMap = currentScoreMap.toMutableMap()
            for (category in currentScoreMap.keys) {
                updatedScoreMap[category] = 0
            }
            docRef.update("score", updatedScoreMap)
            setScore()
        }
    }

    private fun resetNickname(){
        val oldNickname: String = userManager.getLoggedInNickname().toString()
        val newNickname: String = etChangeNickname.text.toString()
        val db = FirebaseFirestore.getInstance()
        val docId = userManager.getLoggedInUserId().toString()
        val docRef = db.collection("users").document(docId)

        if (newNickname != "") {
            if (oldNickname != newNickname) {
                tvErrorNickname.visibility = View.GONE
                docRef.update("nickname", newNickname)
                    .addOnSuccessListener {
                        setNickname()
                        showMainProfilePage()
                        etChangeNickname.text.clear()
                        // Update UI here after successful update
                    }
                    .addOnFailureListener { e ->
                        tvErrorNickname.text = "Error updating nickname: ${e.message}"
                        tvErrorNickname.visibility = View.VISIBLE
                    }
            } else {
                tvErrorNickname.text = "You have the same nickname!"
                tvErrorNickname.visibility = View.VISIBLE
            }
        } else {
            tvErrorNickname.text = "Empty nickname not allowed!"
            tvErrorNickname.visibility = View.VISIBLE
        }
    }

    private fun setNickname(){
        val db = FirebaseFirestore.getInstance()
        val docId = userManager.getLoggedInUserId().toString()
        val docRef = db.collection("users").document(docId)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val nickname = documentSnapshot.get("nickname")?.toString() ?: ""
            tvCurrentNickname.text = "Your current nickname is ${nickname}"
            tvPrincipalText.text = "Hello, ${nickname}"
        }.addOnFailureListener { e ->
            // Handle potential errors (e.g., document not found)
            println("Error getting nickname: $e")
        }
        //val nickname: String = userManager.getLoggedInNickname().toString()

    }

    /*private fun getNickname(){

        val docRef = db.collection("users").document(userId)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val nickname = documentSnapshot.get("nickname")?.toString() ?: ""
            // Use the retrieved nickname here (e.g., update UI)
        }.addOnFailureListener { e ->
            // Handle potential errors (e.g., document not found)
            println("Error getting nickname: $e")
        }*/
    //}

    private fun setScore(){
        val db = FirebaseFirestore.getInstance()
        val docId = userManager.getLoggedInUserId().toString()
        val docRef = db.collection("users").document(docId)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val currentScoreMap = documentSnapshot.get("score") as? Map<String, Int> ?: emptyMap()
            val totalScore = (currentScoreMap["total"] as? Number)?.toInt() ?: 0
            tvSecondaryText.text = "Your Score: ${totalScore}"
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}