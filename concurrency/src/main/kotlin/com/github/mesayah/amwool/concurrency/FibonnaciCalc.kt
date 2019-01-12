package pl.mesayah.amwool.concurrency

import kotlinx.coroutines.*
import kotlin.system.measureNanoTime

object FibonnaciCalc {
    fun calcSync(): Long = measureNanoTime {
        (1..100).map { n ->
            if (n == 1) return@map 1
            else return@map (n - 1) + (n - 2)
        }
    }

    suspend fun calcAsync(): Long = measureNanoTime {
        val deferred = (1..100).map { n ->
            GlobalScope.async {
                if (n == 1) return@async 1
                else return@async (n - 1) + (n - 2)
            }
        }

        deferred.forEach { it.await() }
    }
}