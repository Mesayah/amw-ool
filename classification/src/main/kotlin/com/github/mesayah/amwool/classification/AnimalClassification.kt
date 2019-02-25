package com.github.mesayah.amwool.classification

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
import java.util.logging.Logger

val logger: Logger = Logger.getGlobal()

fun main(args: Array<String>) = args[0].let { dataFilePath ->

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