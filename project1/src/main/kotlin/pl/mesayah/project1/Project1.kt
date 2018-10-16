package pl.mesayah.project1

import kotlinx.coroutines.experimental.launch

fun main(args: Array<String>) {

    println("I will download 10 paragraphs of Lorem Ipsum text from a public API.")
    launch {
        val timeSync = LoremIpsumDownloader.downloadSync()
        val timeAsync = LoremIpsumDownloader.downloadAsync()

        println("Sync time: $timeSync")
        println("Async time: $timeAsync")

        print("Press any key to proceed...")
    }

    readLine()

    println("\nI will calculate 15 first elements of Fibonacci sequence.")
    launch {
        val timeSync = FibonnaciCalc.calcSync()
        val timeAsync = FibonnaciCalc.calcAsync()

        println("Sync time: $timeSync")
        println("Async time: $timeAsync")

        print("Press any key to proceed...")
    }

    readLine()
}

