package com.github.mesayah.amwool.regression

import com.github.mesayah.amwool.mlcommon.LearnCommand
import weka.classifiers.Classifier
import weka.classifiers.functions.LinearRegression
import weka.core.Instances
import weka.core.converters.CSVLoader
import weka.core.converters.Loader

fun main(args: Array<String>) = RegressionLearningCommand().main(args)

class RegressionLearningCommand : LearnCommand() {
    override val classifier: Classifier = LinearRegression()
    override val dataLoader: Loader = CSVLoader()

    override fun Instances.prepareData() = prepareDataForRegression()
}

fun Instances.prepareDataForRegression() = this.apply {
    setClassIndex(numAttributes() - 2)
    deleteAttributeAt(numAttributes() - 1)
}
