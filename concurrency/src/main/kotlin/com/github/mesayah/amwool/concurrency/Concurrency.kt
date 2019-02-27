package com.github.mesayah.amwool.concurrency

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun main(args: Array<String>) = object : CliktCommand() {

    val count by option(names = *arrayOf("--count", "-c"), help = "Number of paragraphs to download.").int().default(10)

    override fun run() {

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
}.main(args)

