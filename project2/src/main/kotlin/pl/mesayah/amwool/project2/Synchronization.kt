package pl.mesayah.amwool.project2

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.system.measureTimeMillis

const val K = 1000
const val N = 100


suspend fun CoroutineScope.massiveRun(action: suspend () -> Unit) {
    val time = measureTimeMillis {
        val jobs = List(K) {
            launch {
                repeat(N) { action() }
            }
        }
        jobs.forEach { it.join() }
    }
    println("Completed ${K * N} actions in $time ms")
}

val mutex = Mutex()
var counter = 0

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
}