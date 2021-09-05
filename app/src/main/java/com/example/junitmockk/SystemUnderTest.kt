package com.example.junitmockk

class SystemUnderTest(val dependency: DependencyClass) {
    fun getResult(): Int {
        return dependency.doCalculate()
    }

    fun getResWithParam(arg: Int): Int {
        return dependency.doCalculateWithParam(arg)
    }
}