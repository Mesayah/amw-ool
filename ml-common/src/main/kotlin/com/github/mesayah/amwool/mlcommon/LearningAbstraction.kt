package com.github.mesayah.amwool.mlcommon

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import weka.classifiers.Classifier
import weka.clusterers.Clusterer
import weka.core.Instances
import weka.core.SerializationHelper
import weka.core.converters.ConverterUtils
import java.io.File
import java.io.IOException
import java.util.logging.Logger

private val logger = Logger.getGlobal()

interface PrepareData<T> {
    fun Instances.prepare(): Instances
}

/**
 * Type class defining learning functionality for all machine learning models.
 */
interface Learn<T> {
    fun T.buildModel(data: Instances): T
}

/**
 * [Learn] implementation for [Classifier]s.
 */
class ClassifierLearn<T : Classifier> : Learn<T> {
    override fun T.buildModel(data: Instances): T = this.apply { buildClassifier(data) }
}

/**
 * [Learn] implementation for [Clusterer]s.
 */
class ClustererLearn<T : Clusterer> : Learn<T> {
    override fun T.buildModel(data: Instances): T = apply { buildClusterer(data) }
}

/**
 * Type class for model serialization and saving to file.
 */
class Save<T> {
    fun T.saveTo(file: File) = file.apply {
        SerializationHelper.write(path, this@saveTo)
    }
}

/**
 * Abstraction for model learning CLI command.
 */
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

    abstract val prepareDataTypeclass: PrepareData<T>
    abstract val learnTypeclass: Learn<T>
    abstract val saveTypeclass: Save<T>
    abstract val model: T

    override fun run() {
        try {
            val data = dataFile.loadDataInstances()
                .apply {
                    prepareDataTypeclass.run { this@apply.prepare() }
                }

            learnTypeclass
                .run { model.buildModel(data) }

            outputFile?.let {
                try {
                    saveTypeclass.run { model.saveTo(it) }
                    print("Saved model to file ${outputFile!!.path}")
                } catch (ioException: IOException) {
                    throw IOException("Could not save model to file", ioException)
                }
            }
        } catch (e: Exception) {
            logger.severe("${e.localizedMessage}: ${e.cause?.localizedMessage}")
        }
    }
}

fun File.loadDataInstances(): Instances {
    print("Loading data instances from file ${this.path}...")
    try {
        return with(ConverterUtils.DataSource(path)) {
            if (dataSet == null) throw IOException("Data could not be loaded")
            dataSet.apply {
                print("${this.numInstances()} data instances loaded")
            }
        }
    } catch (illegalArgumentExc: IllegalArgumentException) {
        throw IllegalArgumentException("Invalid file or file format", illegalArgumentExc)
    }
}


