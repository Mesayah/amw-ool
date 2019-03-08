package com.github.mesayah.amwool.classification

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import java.util.logging.Logger

val logger: Logger = Logger.getGlobal()

fun main(args: Array<String>) = object : CliktCommand() {
    override fun run() = Unit
}.subcommands(LearnTreeCommand, EvaluateTreeCommand, ClassifyCommand)
    .main(args)