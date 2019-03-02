package com.github.mesayah.amwool.mlcommon

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import weka.classifiers.Classifier
import weka.classifiers.evaluation.Evaluation
import weka.core.Instances
import weka.core.SerializationHelper
import weka.core.converters.Loader
import java.io.File
import java.io.IOException
import java.util.*
import java.util.logging.Logger

val logger = Logger.getGlobal()

abstract class LearnCommand : CliktCommand(name = "learn", help = "Provide learning data to teach the program") {

    private val dataFile by option(
        names = *arrayOf("--data", "-d"),
        help = "Path to file containing learning data"
    ).file().required()

    private val outputFile by option(
        names = *arrayOf("-f", "--file"),
        help = "File name for saving model"
    ).file()

    abstract val classifier: Classifier
    abstract val dataLoader: Loader
    abstract fun Instances.prepareData(): Instances

    override fun run() {
        try {
            with(dataFile.loadDataInstances(dataLoader)) data@{
                prepareData()
                    .buildModel(classifier).apply {
                        evaluate(this@data)
                        outputFile?.let { this.saveTo(it) }
                    }
            }
        } catch (e: IOException) {
            logger.severe("${e.localizedMessage}: ${e.cause?.localizedMessage}")
        }
    }
}

fun Classifier.saveTo(outputFile: File) = outputFile.apply {
    try {
        SerializationHelper.write(outputFile.path, this@saveTo)
        logger.info("Saved model to file ${outputFile.path}")
    } catch (ioException: IOException) {
        throw IOException("Could not saveTo model to file", ioException)
    }
}

fun Classifier.evaluate(evaluationData: Instances) = Evaluation(evaluationData)
    .apply {
        crossValidateModel(this@evaluate, evaluationData, 10, Random(1))
        logger.info(toSummaryString("Evaluation", true))
    }

fun Instances.buildModel(classifier: Classifier) = classifier
    .apply {
        buildClassifier(this@buildModel)
    }

fun File.loadDataInstances(loader: Loader): Instances = with(loader) {
    setSource(this@loadDataInstances)
    if (dataSet == null) throw IOException("Data could not be loaded")
    dataSet.apply {
        logger.info("${this.numInstances()} data instances loaded")
    }
}
