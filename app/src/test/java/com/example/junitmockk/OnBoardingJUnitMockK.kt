package com.example.junitmockk

import androidx.annotation.VisibleForTesting
import com.github.technoir42.rxjava2.junit5.OverrideSchedulersExtension
import io.mockk.*
import io.reactivex.rxjava3.core.Flowable
import org.junit.Assert.*
import org.junit.Test
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.*

class OnBoardingJUnitMockK {

    private val dependency = mockk<DependencyClass>()

    @ExtendWith(OverrideSchedulersExtension::class)
    @Test
    fun `test RxJava Rule`() {
        Flowable.just("RxJavaRule")
    }

    @Test
    fun `test private field`() {
        every { dependency.doCalculate() } returns 10
        val sut = SystemUnderTest(dependency)

        assertEquals(10, sut.dependency.doCalculate())
    }

    @Test
    fun `test relaxed mock`() {
        val relaxedMock = mockk<DependencyClass>(relaxed = true)
        assertEquals(0, relaxedMock.doCalculate())
    }

    @Test
    fun `test relax unit fun`() {
        val relaxUnitFun = mockk<DependencyClass>(relaxUnitFun = true)
        assertEquals(Unit, relaxUnitFun.fustRuns())
    }

    @Test
    fun `test assert with double`() {
        assertEquals(0.12345678_9, 0.1234567_9, 0.00001)
    }

    @Test
    fun `test argument matching`() {
        every {
            dependency.doCalculateWithParam(
                and(
                    or(eq(2), less(-2, andEquals = true)),
                    range(-6, -1)
                )
            )
        } returns 0

        assertEquals(0, dependency.doCalculateWithParam(-5))
    }

    @Test
    fun `test answers`() {
        every { dependency.doCalculateWithParam(any()) } answers { firstArg() }
        assertEquals(100, dependency.doCalculateWithParam(100))
    }

    @Test
    fun `test slots`() {
        val slot = slot<Int>()
        every { dependency.doCalculateWithParam(capture(slot)) } answers {
            if (slot.captured in 0..10) 5
            else -5
        }
        assertAll(
            { assertEquals(5, dependency.doCalculateWithParam(10)) },
            { assertEquals(10, slot.captured) },
            { assertEquals(-5, dependency.doCalculateWithParam(15)) }
        )
    }

    @Test
    fun `test verify`() {
        every { dependency.doCalculateWithParam(1) } returns 1
        every { dependency.doCalculateWithParam(2) } returns 2

        val sut = SystemUnderTest(dependency)
        val callWithParam1 = 2
        val callWithParam2 = 4

        for (i in 1..callWithParam1) {
            sut.getResWithParam(1)
        }
        for (i in 1..callWithParam2) {
            sut.getResWithParam(2)
        }

        val notCalledMock = mockk<DependencyClass>()

        verify(atLeast = callWithParam1 - 1, atMost = callWithParam1 + 1) {
            dependency.doCalculateWithParam(1)
        }
        verify(exactly = callWithParam2) {
            dependency.doCalculateWithParam(2)
        }
        verify { notCalledMock wasNot Called }

        confirmVerified(dependency)

        every { dependency.doCalculate() } returns 0
        dependency.doCalculate()

        verifyAll {
            dependency.doCalculateWithParam(1)
            dependency.doCalculateWithParam(2)
            dependency.doCalculate()
        }
        verifyOrder {
            dependency.doCalculateWithParam(1)
            dependency.doCalculate()
        }
        verifySequence {
            dependency.doCalculateWithParam(1)
            dependency.doCalculateWithParam(1)
            dependency.doCalculateWithParam(2)
            dependency.doCalculateWithParam(2)
            dependency.doCalculateWithParam(2)
            dependency.doCalculateWithParam(2)
            dependency.doCalculate()
        }
    }

    @Test
    fun `test returns many`() {
        every { dependency.doCalculate() } returnsMany listOf(1, 2, 3)
        assertAll(
            { assertEquals(1, dependency.doCalculate()) },
            { assertEquals(2, dependency.doCalculate()) },
            { assertEquals(3, dependency.doCalculate()) },
            { assertEquals(3, dependency.doCalculate()) },
            { assertEquals(3, dependency.doCalculate()) }
        )
    }
}