package com.github.mesayah.amwool.classification

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int
import weka.attributeSelection.AttributeSelection
import weka.attributeSelection.InfoGainAttributeEval
import weka.attributeSelection.Ranker
import weka.classifiers.trees.J48
import weka.core.Attribute
import weka.core.Instances
import weka.core.SerializationHelper
import weka.core.converters.ConverterUtils
import java.io.File
import java.io.IOException

object LearnCommand : CliktCommand(name = "learn", help = "Use training data set to teach program") {
    private val dataFile by option(
        names = *arrayOf("--data", "-d"),
        help = "Path to file containing learning data"
    ).file().required()

    private val attributeLimit by option(
        names = *arrayOf("-a", "--attributes"),
        help = "Number of the most important attributes to use, 0 for no limit"
    ).int().default(0)

    private val outputFile by option(
        names = *arrayOf("-f", "--file"),
        help = "File name for saving model"
    ).file()

    override fun run() {
        val filteredDataInstances = removeAnimalNameAttribute(loadDataInstances(dataFile.path))
        val mostImportantAttributesIndices = selectMostImportantAttributes(filteredDataInstances)
        val dataInstances = limitAttributes(mostImportantAttributesIndices, filteredDataInstances, attributeLimit)
        val tree = buildTree(dataInstances)
        outputFile?.let { tree.saveTo(it) }
    }
}

private fun J48.saveTo(outputFile: File) = {
    try {
        SerializationHelper.write(outputFile.path, this)
        logger.info("Saved model to file ${outputFile.path}.")
    } catch (ioException: IOException) {
        throw IOException("Could not save model to file.", ioException)
    }
}


private fun limitAttributes(
    mostImportantAttributes: List<Attribute>,
    dataInstances: Instances,
    attributeLimit: Int
): Instances = dataInstances.apply {
    mostImportantAttributes
        .take(attributeLimit)
        .forEach { deleteAttributeAt(it.index()) }
}


fun buildTree(filteredDataInstances: Instances): J48 = J48().apply {
    options = arrayOf("-U")
    buildClassifier(filteredDataInstances)
    logger.info("Built tree: $this.")
}

fun selectMostImportantAttributes(dataInstances: Instances): List<Attribute> = with(AttributeSelection()) {
    apply {
        setEvaluator(InfoGainAttributeEval())
        setSearch(Ranker())
        SelectAttributes(dataInstances)
    }
    selectedAttributes()
        .map { dataInstances.attribute(it) }
        .apply { logger.info("Selected the most important attributes: ${map { Attribute::name }}.") }
}

fun removeAnimalNameAttribute(dataInstances: Instances): Instances = dataInstances.apply {
    deleteAttributeAt(0)
}


fun loadDataInstances(dataFileName: String): Instances = with(ConverterUtils.DataSource(dataFileName)) {
    if (dataSet == null) throw IOException("Data could not be loaded.")
    dataSet.apply {
        logger.info("${this.numInstances()} data instances loaded.")
    }
}