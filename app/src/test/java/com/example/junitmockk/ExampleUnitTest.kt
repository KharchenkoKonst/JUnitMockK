package com.example.junitmockk

import io.mockk.*
import org.junit.Test
import org.junit.Assert.*
import org.junit.jupiter.api.assertAll

class ExampleUnitTest {
    private val mock = mockk<DependencyClass>()

    @Test
    fun testMock() {
        every { mock.doCalculate() } returns 10
        every { mock.doCalculateWithParam(or(more(0), less(10))) } answers { firstArg() }
        every { mock.doCalculateWithParam(or(less(0), more(10))) } answers { firstArg() }

        val sut = SystemUnderTest(mock)
        val arg = 10
        assertAll(
            { assertEquals(10, sut.getResult()) },
            { assertEquals(arg, sut.getResWithParam(arg)) },
            { assertEquals(arg.unaryMinus(), sut.getResWithParam(arg.unaryMinus())) }
        )

        verifyAll {
            mock.doCalculate()
            mock.doCalculateWithParam(arg)
            mock.doCalculateWithParam(arg.unaryMinus())
        }
        verifySequence {
            mock.doCalculate()
            mock.doCalculateWithParam(arg)
            mock.doCalculateWithParam(arg.unaryMinus())
        }
        verifyOrder {
            mock.doCalculate()
            mock.doCalculateWithParam(arg.unaryMinus())
        }
    }
}