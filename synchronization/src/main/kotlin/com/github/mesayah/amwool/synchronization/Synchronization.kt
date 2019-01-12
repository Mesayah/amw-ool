package com.github.mesayah.amwool.synchronization

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.system.measureTimeMillis

const val K = 1000
const val N = 100

/**
 * Launches [K] jobs repeating [action] [N] times and joins them in the end.
 * @return duration of all jobs execution in milliseconds
 */
suspend fun CoroutineScope.massiveRun(action: suspend () -> Unit): Long {
    val time = measureTimeMillis {
        val jobs = List(K) {
            launch {
                repeat(N) { action() }
            }
        }
        jobs.forEach { it.join() }
    }
    println("Completed ${K * N} actions in $time ms")
    return time
}

/**
 * [Mutex] for locking shared resources in a coroutine scope.
 */
val mutex = Mutex()
var counter = 0

/**
 * Executes [massiveRun] with and without the [mutex].
 */
fun main() = runBlocking {
    GlobalScope.massiveRun {
        mutex.withLock {
            counter++
        }
    }
    println("Synchronized Counter = $counter")

    counter = 0
    GlobalScope.massiveRun {
        counter++
    }
    println("Not Synchronized Counter = $counter")

    println("Conclusion: not synchronized counter should never equals ${N * K} on multicore processors.")
}