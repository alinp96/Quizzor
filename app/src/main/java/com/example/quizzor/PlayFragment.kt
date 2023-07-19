package com.example.quizzor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_play, container, false)
        val quizCategoryDropDown = view.findViewById<Spinner>(R.id.spnQuizDropDown)
        val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.quizCategories, android.R.layout.simple_spinner_item)
        // create adapter for Quiz Category drop down
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        quizCategoryDropDown.adapter = adapter
        // Set as default the first category TODO: Maybe randomize it in the future
        quizCategoryDropDown.setSelection(0)
        // Get the value from adapter
        quizCategoryDropDown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = quizCategoryDropDown.selectedItem as String
                // Use the selected item as needed
                Log.d("TAG", selectedItem)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case where nothing is selected, if needed
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
         * @return A new instance of fragment PlayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            PlayFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}