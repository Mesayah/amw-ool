package com.github.mesayah.amwool.classification

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int

class LearnCommand : CliktCommand(name = "learn", help = "Use training data set to teach program") {
    val dataFile by option(names = *arrayOf("--data", "-d"), help = "Path to file containing learning data").file().required()
    val attributeLimit by option(names = *arrayOf("-a", "--attributes"), help = "Number of the most important attributes to use, 0 for no limit").int().default(0)

    override fun run() {
        val dataInstances = loadDataInstances(dataFile.path)
        val filteredDataInstances = removeAnimalNameAttribute(dataInstances)
        val mostImportantAttributesIndices = selectMostImportantAttributes(filteredDataInstances)
        if (attributeLimit > 0) {
            mostImportantAttributesIndices.take(attributeLimit)

        }
        val tree = buildTree(filteredDataInstances)
    }
}