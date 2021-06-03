package com.rsschool.android2021

/**
 *  Interface for transferring data between fragments via MainActivity as a host.
 */
interface Communicator {
    /**
     *  Method to send [result] data from the SecondFragment to the FirstFragment.
     */
    fun passResult(result: Int)

    /**
     *  Method to send [min] and [max] data from the FirstFragment to the SecondFragment.
     */
    fun passValues(min: Int, max: Int)
}