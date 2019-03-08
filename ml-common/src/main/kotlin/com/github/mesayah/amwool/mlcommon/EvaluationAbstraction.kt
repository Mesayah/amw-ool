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
import java.util.*
import java.util.logging.Logger

private val logger = Logger.getGlobal()

/**
 * Type class defining evaluation functionality for all machine learning models.
 */
interface Evaluate<T, R> {
    fun T.evaluate(data: Instances): R

    fun R.printEvaluationSummary(): Unit
}

/**
 * [Evaluate] implementation for [Classifier]s.
 */
class ClassifierEvaluate<T : Classifier> : Evaluate<T, Evaluation> {
    override fun Evaluation.printEvaluationSummary() = print(this.toSummaryString("Evaluation summary", true))

    override fun T.evaluate(data: Instances): Evaluation = Evaluation(data).apply {
        crossValidateModel(this@evaluate, data, 10, Random())
    }
}

/**
 * [Evaluate] implementation for [Clusterer]s.
 */
class ClustererEvaluate<T : DensityBasedClusterer> : Evaluate<T, Double> {
    override fun Double.printEvaluationSummary() = print("Clustering log likelihood: $this")

    override fun T.evaluate(data: Instances): Double =
        ClusterEvaluation.crossValidateModel(this@evaluate, data, 10, Random())
}

/**
 * Abstraction for model learning CLI command.
 */
abstract class AbstractEvaluateCommand<T, R> :
    CliktCommand(name = "evaluate", help = "Evaluate learnt model") {

    private val modelFile by option(
        names = *arrayOf("--model", "-m"),
        help = "Path to file containing serialized model"
    ).file().required()

    private val dataFile by option(
        names = *arrayOf("--data", "-d"),
        help = "Path to file containing evaluation data"
    ).file().required()

    abstract val evaluateTypeclass: Evaluate<T, R>
    val model: T by lazy { SerializationHelper.read(modelFile.path) as T }

    override fun run() {
        try {
            val data = dataFile.loadDataInstances()

            evaluateTypeclass
                .run {
                    model
                        .evaluate(data)
                        .printEvaluationSummary()
                }
        } catch (e: Exception) {
            logger.severe("${e.localizedMessage}: ${e.cause?.localizedMessage}")
        }
    }
}


