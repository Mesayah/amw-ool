package com.github.mesayah.amwool.concurrency

import kotlinx.coroutines.runBlocking
import org.junit.Test

class FibonacciCalcTest {

    private val correctFibonnaciResults = mapOf<Int, Long>(
        0 to 0,
        1 to 1,
        2 to 1,
        3 to 2,
        4 to 3,
        5 to 5,
        6 to 8,
        7 to 13,
        8 to 21,
        9 to 34,
        10 to 55,
        11 to 89,
        12 to 144,
        13 to 233,
        14 to 377,
        15 to 610,
        16 to 987,
        17 to 1597,
        18 to 2584,
        19 to 4181
    )

    @Test
    fun shouldComputeCorrectFibonacciNumber() {
        correctFibonnaciResults.forEach {(n, result) ->
            assert(fibonacciSync(n) == result)
            runBlocking {
                assert(fibonacciAsync(n) == result)
            }
        }
    }
}