package com.github.mesayah.amwool.classification

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int
import weka.classifiers.trees.J48
import weka.core.DenseInstance
import weka.core.Instance
import weka.core.SerializationHelper
import java.io.File
import java.io.IOException

object Classification : CliktCommand(name = "learn", help = "Use training data set to teach program") {

    private val modelFile: File by option("--model", "-m").file().required()

    private val hair: Boolean? by option("--hair").flag(default = false)
    private val feathers: Boolean? by option("--feathers").flag(default = false)
    private val eggs: Boolean? by option("--eggs").flag(default = false)
    private val milk: Boolean? by option("--milk").flag(default = false)
    private val airborne: Boolean? by option("--airborne").flag(default = false)
    private val aquatic: Boolean? by option("--aquatic").flag(default = false)
    private val predator: Boolean? by option("--predator").flag(default = false)
    private val toothed: Boolean? by option("--toothed").flag(default = false)
    private val backbone: Boolean? by option("--backbone").flag(default = false)
    private val breathes: Boolean? by option("--breathes").flag(default = false)
    private val venomous: Boolean? by option("--venomous").flag(default = false)
    private val fins: Boolean? by option("--fins").flag(default = false)
    private val legs: Int? by option("--legs").int()
    private val tail: Boolean? by option("--tail").flag(default = false)
    private val domestic: Boolean? by option("--domestic").flag(default = false)
    private val catsize: Boolean? by option("--catsize").flag(default = false)

    override fun run() {
        val tree = loadTreeFromFile(modelFile)
        val dataValues = arrayOf(
            hair,
            feathers,
            eggs,
            milk,
            airborne,
            aquatic,
            predator,
            toothed,
            backbone,
            breathes,
            venomous,
            fins,
            legs,
            tail,
            domestic,
            catsize
        )
        val instance = buildDataInstance(dataValues)
        val classificationResult = tree.classifyInstance(instance)
        println("Result: ${instance.classAttribute().value(classificationResult.toInt())}")
    }
}

private fun buildDataInstance(dataValues: Array<Any?>): Instance =
    dataValues.map {
        when (it) {
            is Boolean -> if (it) 1.0 else 0.0
            is Int -> it.toDouble()
            else -> throw IllegalArgumentException("Data can be only numeric or logical.")
        }
    }.toDoubleArray().let { DenseInstance(1.0, it) }

private fun loadTreeFromFile(file: File): J48 {
    try {
        val tree = SerializationHelper.read(file.path) as J48
        logger.info("Read model from file ${file.path}.")
        return tree
    } catch (ioException: IOException) {
        throw IOException("Could not read model from file.", ioException)
    }
}