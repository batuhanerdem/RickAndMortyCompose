package com.example.rickandmortycompose.utils

object IntUtils {
    fun getGreater(numberOne: Int, numberTwo: Int): Int {
        return if (numberOne > numberTwo) numberOne
        else numberTwo
    }

    fun getLower(numberOne: Int, numberTwo: Int): Int {
        return if (numberOne < numberTwo) numberOne
        else numberTwo
    }
}