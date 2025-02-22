package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import kotlin.random.Random

class SecondFragment : Fragment() {

    private var backButton: Button? = null
    private var result: TextView? = null
    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        communicator = activity as Communicator
        /**
         *  Customize Back navigation (<) button navigating to the FirstFragment
         */
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            communicator.passResult(result?.text.toString().toInt())
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0

        result?.text = generate(min, max).toString()

        backButton?.setOnClickListener {
            /**
             *  Transfer [result] data to the FirstFragment.
             */
            communicator.passResult(result?.text.toString().toInt())
        }
    }

    private fun generate(min: Int, max: Int): Int {
        /**
         *  Default random value uniformly distributed between the specified [min] (inclusive)
         *  and [max] (exclusive) bounds. In order to suite the task and generate
         *  random value between [min] (inclusive) and [max] (inclusive) bounds
         *  the [max] value is incremented by +1.
         */
        return Random.nextInt(min, max + 1)
    }

    companion object {

        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()

            args.putInt(MIN_VALUE_KEY, min)
            args.putInt(MAX_VALUE_KEY, max)
            fragment.arguments = args

            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }
}