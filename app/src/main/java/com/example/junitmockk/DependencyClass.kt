package com.example.junitmockk

import androidx.annotation.VisibleForTesting

class DependencyClass(private val value: Int) {

    fun doCalculate(): Int {
        return value
    }

    fun doCalculateWithParam(value: Int): Int {
        return this.value + value
    }

    fun fustRuns() = Unit
}