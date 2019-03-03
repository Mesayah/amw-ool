package com.github.mesayah.amwool.regression

import com.github.mesayah.amwool.mlcommon.AbstractLearningTest
import weka.classifiers.Classifier
import weka.classifiers.functions.LinearRegression
import weka.core.Instances
import weka.core.converters.CSVLoader
import weka.core.converters.Loader

class RegressionLearningTest : AbstractLearningTest() {
    override fun Instances.prepareData(): Instances = prepareDataForRegression()
    override val loaderSupplier: () -> Loader = { CSVLoader() }
    override val classifierSupplier: () -> Classifier = { LinearRegression() }
    override val dataResourceFileName: String = "data.csv"
}