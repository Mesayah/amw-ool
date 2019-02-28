package com.github.mesayah.amwool.concurrency

import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

fun downloadSync(n: Int, url: String) =
    (1..n).map {
        val (_, _, result) = url.httpGet().responseString()
        result.get()
    }.reduce(String::plus)


suspend fun downloadAsync(n: Int, url: String) =
    (1..n).map {
        GlobalScope.async {
            val (_, _, result) = url.httpGet().responseString()
            result.get()
        }
    }.map { it.await() }
        .reduce(String::plus)
