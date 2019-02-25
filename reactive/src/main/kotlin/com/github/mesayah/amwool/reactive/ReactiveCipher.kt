package com.github.mesayah.amwool.reactive

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands

fun main(args: Array<String>) = object : CliktCommand() {
    override fun run() = Unit
}.subcommands(
    EncryptCommand,
    DecryptCommand
).main(args)

const val DEFAULT_ALGORITHM = "DES"

