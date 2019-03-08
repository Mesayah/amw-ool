package com.github.mesayah.amwool.concurrency

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) = object : CliktCommand() {

    val count by option(names = *arrayOf("--count", "-c"), help = "Number of paragraphs to download.").int().default(20)

    override fun run() {
        println("I will execute example HTTP request $count times.")
        compareDownloading(count)

        print("Press any key to proceed...")
        readLine()

        println("I will calculate first $count elements of Fibonacci sequence.")
        compareComputation(count)

        print("Press any key to proceed...")
        readLine()
    }
}.main(args)

private fun compareComputation(n: Int) {
    runBlocking {
        val timeSync = measureTimeMillis { fibonacciSync(n) }
        val timeAsync = measureTimeMillis { fibonacciAsync(n) }

        println("Sync time: $timeSync")
        println("Async time: $timeAsync")
    }
}

const val URL = "https://httpbin.org/get"

private fun compareDownloading(n: Int) {
    runBlocking {
        val timeSync = measureTimeMillis { downloadSync(n, URL) }
        val timeAsync = measureTimeMillis { downloadAsync(n, URL) }

        println("Sync time: $timeSync")
        println("Async time: $timeAsync")
    }
}

