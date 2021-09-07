package com.example.junitmockk

import androidx.annotation.VisibleForTesting

class SystemUnderTest(@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE) val dependency: DependencyClass) {

    fun getResult(): Int {
        return dependency.doCalculate()
    }

    fun getResWithParam(param: Int): Int {
        return dependency.doCalculateWithParam(param)
    }
}