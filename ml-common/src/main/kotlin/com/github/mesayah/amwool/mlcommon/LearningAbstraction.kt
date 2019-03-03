package com.github.mesayah.amwool.mlcommon

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import weka.classifiers.Classifier
import weka.classifiers.Evaluation
import weka.clusterers.ClusterEvaluation
import weka.clusterers.Clusterer
import weka.clusterers.DensityBasedClusterer
import weka.core.Instances
import weka.core.SerializationHelper
import weka.core.converters.ConverterUtils
import java.io.File
import java.io.IOException
import java.util.*
import java.util.logging.Logger

val logger = Logger.getGlobal()


abstract class AbstractLearnCommand<T> :
    CliktCommand(name = "learn", help = "Provide learning data to teach the program") {

    private val dataFile by option(
        names = *arrayOf("--data", "-d"),
        help = "Path to file containing learning data"
    ).file().required()

    private val outputFile by option(
        names = *arrayOf("-f", "--file"),
        help = "File name for saving model"
    ).file()

    abstract val model: T
    abstract fun Instances.prepareData(): Instances

    override fun run() {
        try {
            with(dataFile.loadDataInstances()) data@{
                prepareData()
                model.buildModel(this).apply {
                    evaluate(this@data)
                    outputFile?.let {
                        try {
                            logger.info("Saved model to file ${outputFile!!.path}")
                        } catch (ioException: IOException) {
                            throw IOException("Could not save model to file", ioException)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            logger.severe("${e.localizedMessage}: ${e.cause?.localizedMessage}")
        }
    }
}

fun <T> T.saveTo(file: File) = file.apply {
    SerializationHelper.write(this.path, this@saveTo)
}

fun <T> T.buildModel(instances: Instances): T =
    when (this) {
        is Classifier -> {
            buildClassifier(instances)
            this
        }
        is Clusterer -> {
            buildClusterer(instances)
            this
        }
        else -> throw java.lang.IllegalStateException("Model building is not handled")
    }

private fun <T> T.evaluate(instances: Instances) =
    when (this) {
        is Classifier -> Evaluation(instances).apply {
            crossValidateModel(
                this@evaluate,
                instances,
                10,
                Random(1)
            )
        }.apply { logger.info(toSummaryString()) }
        is DensityBasedClusterer -> ClusterEvaluation.crossValidateModel(
            this,
            instances,
            10,
            Random(1)
        ).apply { logger.info("$this") }
        else -> throw IllegalStateException("Evaluation is not handled")
    }


fun File.loadDataInstances(): Instances {
    try {
        return with(ConverterUtils.DataSource(path)) {
            if (dataSet == null) throw IOException("Data could not be loaded")
            dataSet.apply {
                logger.info("${this.numInstances()} data instances loaded")
            }
        }
    } catch (illegalArgumentExc: IllegalArgumentException) {
        throw IllegalArgumentException("Invalid file or file format", illegalArgumentExc)
    }
}


