package com.example.junitmockk

class DependencyClass(private val value: Int) {
    fun doCalculate(): Int {
        return value
    }

    fun doCalculateWithParam(arg: Int): Int {
        return value + arg
    }
}