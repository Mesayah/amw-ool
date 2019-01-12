package pl.mesayah.amwool.concurrency

import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlin.system.measureNanoTime

object LoremIpsumDownloader {
    fun downloadSync(): Long = measureNanoTime {
        (1..10).map { n ->
            val (request, response, result) = "https://httpbin.org/get".httpGet().responseString()
            result.get()
        }
    }

    suspend fun downloadAsync(): Long = measureNanoTime {
        val deferred = (1..10).map { n ->
            GlobalScope.async {
                val (request, response, result) = "https://httpbin.org/get".httpGet().responseString()
                result.get()
            }
        }

        deferred.forEach { it.await() }
    }
}