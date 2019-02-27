package com.github.mesayah.amwool.classification

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int
import weka.classifiers.trees.J48
import weka.core.Instance
import java.io.File
import kotlin.reflect.KProperty

object Classification : CliktCommand(name = "learn", help = "Use training data set to teach program") {

    val modelFile: File by option("--model", "-m").file().required()

    val hair: Boolean? by option("--hair").flag(default = false)
    val feathers: Boolean? by option("--feathers").flag(default = false)
    val eggs: Boolean? by option("--eggs").flag(default = false)
    val milk: Boolean? by option("--milk").flag(default = false)
    val airborne: Boolean? by option("--airborne").flag(default = false)
    val aquatic: Boolean? by option("--aquatic").flag(default = false)
    val predator: Boolean? by option("--predator").flag(default = false)
    val toothed: Boolean? by option("--toothed").flag(default = false)
    val backbone: Boolean? by option("--backbone").flag(default = false)
    val breathes: Boolean? by option("--breathes").flag(default = false)
    val venomous: Boolean? by option("--venomous").flag(default = false)
    val fins: Boolean? by option("--fins").flag(default = false)
    val legs: Int? by option("--legs").int()
    val tail: Boolean? by option("--tail").flag(default = false)
    val domestic: Boolean? by option("--domestic").flag(default = false)
    val catsize: Boolean? by option("--catsize").flag(default = false)

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
        println(getResultString(classificationResult))
    }

    private fun getResultString(classificationResult: Double): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun buildDataInstance(dataValues: Array<Any?>): Instance {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun loadTreeFromFile(file: File): J48 {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}