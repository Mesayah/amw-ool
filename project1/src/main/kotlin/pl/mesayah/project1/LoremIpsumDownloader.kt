package pl.mesayah.project1

import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.experimental.async
import kotlin.system.measureNanoTime

object LoremIpsumDownloader {
    fun downloadSync(): Long = measureNanoTime {
        (1..10).map { n ->
            val (request, response, result) = "https://httpbin.org/get".httpGet().responseString()
            result.component1()
        }
    }

    suspend fun downloadAsync(): Long = measureNanoTime {
        val deferred = (1..10).map { n ->
            async {
                val (request, response, result) = "https://httpbin.org/get".httpGet().responseString()
                result.component1()
            }
        }

        deferred.forEach { it.await() }
    }
}