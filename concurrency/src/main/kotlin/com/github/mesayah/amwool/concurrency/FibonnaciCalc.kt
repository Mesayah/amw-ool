package com.github.mesayah.amwool.concurrency

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlin.system.measureNanoTime

fun fibonacciSync(n: Int): Long {
    return if (n == 1 || n == 0) n.toLong()
    else fibonacciSync(n - 1) + fibonacciSync(n - 2)
}

suspend fun fibonacciAsync(n: Int): Long =
    GlobalScope.async {
        if (n == 1 || n == 0) return@async n.toLong()
        else return@async fibonacciAsync(n - 1) + fibonacciAsync(n - 2)
    }.await()
