package com.github.mesayah.amwool.messaging.consumer

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import com.rabbitmq.client.Delivery

fun main(args: Array<String>) = Consume().main(args)

class Consume : CliktCommand() {
    val host: String by option(help = "RabbitMQ host").default("localhost")
    val port: Int by option(help = "RabbitMQ port").int().default(5672)
    val queue: String by option(help = "RabbitMQ queue name").required()

    override fun run() {
        try {
            ConnectionFactory().apply {
                host = this@Consume.host
                port = this@Consume.port

                newConnection()
                    .createChannel().apply {
                        queueDeclare(queue, false, false, false, null)
                        println("Waiting for messages...")
                        basicConsume(queue,
                            true,
                            { tag, message -> println("New message received: ${String(message.body)}") },
                            { tag -> Unit })
                    }
            }
        } catch (exception: Exception) {
            println("ERROR: ${exception.localizedMessage}")
        }
    }
}

object MessageDeliverCallback : DeliverCallback {
    override fun handle(consumerTag: String?, message: Delivery?) {
    }
}