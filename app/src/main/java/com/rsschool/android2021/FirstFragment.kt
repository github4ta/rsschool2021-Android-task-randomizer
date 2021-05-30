package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minValue: TextView? = null
    private var maxValue: TextView? = null
    private lateinit var communicator: Communicator

    private fun validateEnteredValues(minValue: TextView?, maxValue: TextView?): Boolean {
        val MAX_VALUE_TO_GENERATE = Int.MAX_VALUE - 1
        var validation = false
        val min = minValue?.text.toString().toIntOrNull()
        val max = maxValue?.text.toString().toIntOrNull()
        if (min == null || max == null) {
            Toast.makeText(context,"Value(-s) should not be empty.", Toast.LENGTH_SHORT).show()
        } else {
            if (min >= max) {
                Toast.makeText(context,"Max should be greater than Min.", Toast.LENGTH_SHORT).show()
            } else {
                if (max >= MAX_VALUE_TO_GENERATE) {
                    Toast.makeText(context,"Max should be less than $MAX_VALUE_TO_GENERATE.", Toast.LENGTH_SHORT).show()
                } else {
                    validation = true
                }
            }
        }
        return validation
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        communicator = activity as Communicator
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            // No action when user click Back navigation (<) button on the device
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        minValue = view.findViewById(R.id.min_value)
        maxValue = view.findViewById(R.id.max_value)

        generateButton?.setOnClickListener {
            if (validateEnteredValues(minValue, maxValue)) {
                val min: Int = minValue?.text.toString().toInt()
                val max: Int = maxValue?.text.toString().toInt()
                /**
                 *  Transfer [min] and [max] data to the SecondFragment.
                 */
                communicator.passValues(min, max)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}