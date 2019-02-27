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
import weka.core.converters.ConverterUtils
import weka.filters.Filter
import weka.filters.unsupervised.attribute.Remove
import java.io.IOException

object LearnCommand : CliktCommand(name = "learn", help = "Use training data set to teach program") {
    private val dataFile by option(names = *arrayOf("--data", "-d"), help = "Path to file containing learning data").file().required()
    private val attributeLimit by option(names = *arrayOf("-a", "--attributes"), help = "Number of the most important attributes to use, 0 for no limit").int().default(0)

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

fun buildTree(filteredDataInstances: Instances) = J48().apply {
    options = arrayOf("-U")
    buildClassifier(filteredDataInstances)
    logger.info("Built tree: $this")
}

fun selectMostImportantAttributes(dataInstances: Instances): IntArray = with(AttributeSelection()) {
    apply {
        setEvaluator(InfoGainAttributeEval())
        setSearch(Ranker())
        SelectAttributes(dataInstances)
    }
    selectedAttributes().apply {
        logger.info(
            "Selected the most important attributes: " +
                    "${this.map { attributeIndex -> dataInstances.attribute(attributeIndex).name() }}"
        )
    }
}

fun removeAnimalNameAttribute(dataInstances: Instances): Instances = with(Remove()) {
    apply {
        options = arrayOf("-R", "1")
        setInputFormat(dataInstances)
    }
    Filter.useFilter(dataInstances, this)
}

fun removeAttributes(dataInstances: Instances, attributes: List<Attribute>) {
    attributes.forEach {
        dataInstances.deleteAttributeAt(it.index())
    }
}

fun loadDataInstances(dataFileName: String): Instances = with(ConverterUtils.DataSource(dataFileName)) {
    if (dataSet == null) throw IOException("Data could not be loaded.")
    dataSet.apply {
        logger.info("${this.numInstances()} data instances loaded.")
    }
}