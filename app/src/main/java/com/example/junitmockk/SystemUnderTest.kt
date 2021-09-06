package com.example.junitmockk

class SystemUnderTest(private val dependency: DependencyClass) {
    fun getResult(): Int {
        return dependency.doCalculate()
    }

    fun getResWithParam(param: Int): Int {
        return dependency.doCalculateWithParam(param)
    }
}