package com.github.mesayah.amwool.messaging.publisher

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import com.rabbitmq.client.ConnectionFactory

fun main(args: Array<String>) = Publish().main(args)

class Publish : CliktCommand() {
    val host: String by option(help = "RabbitMQ host URL").default("localhost")
    val port: Int by option(help = "RabbitMQ port").int().default(5672)
    val queue: String by option(help = "RabbitMQ queue name").default("default-queue")
    val message: String by argument(help = "Message to send through RabbitMQ")

    override fun run() {
        try {
            ConnectionFactory().apply {
                host = this@Publish.host
                port = this@Publish.port

                with(newConnection()) {
                    createChannel().apply {
                        queueDeclare(queue, false, false, false, null)
                        basicPublish(
                            "",
                            queue,
                            null,
                            message.toByteArray()
                        )
                        println("Sent message \"$message\"")
                        close()
                    }
                    close()
                }
            }
        } catch (exception: Exception) {
            println("ERROR: ${exception.localizedMessage}")
        }
    }
}