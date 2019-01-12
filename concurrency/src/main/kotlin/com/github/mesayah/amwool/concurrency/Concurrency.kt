package com.github.mesayah.amwool.concurrency

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun main(args: Array<String>) {

    println("I will download 10 paragraphs of Lorem Ipsum text from a public API.")
    GlobalScope.launch {
        val timeSync = LoremIpsumDownloader.downloadSync()
        val timeAsync = LoremIpsumDownloader.downloadAsync()

        println("Sync time: $timeSync")
        println("Async time: $timeAsync")

        print("Press any key to proceed...")
    }

    readLine()

    println("\nI will calculate 15 first elements of Fibonacci sequence.")
    GlobalScope.launch {
        val timeSync = FibonnaciCalc.calcSync()
        val timeAsync = FibonnaciCalc.calcAsync()

        println("Sync time: $timeSync")
        println("Async time: $timeAsync")

        print("Press any key to proceed...")
    }

    readLine()
}

