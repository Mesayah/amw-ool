package com.github.mesayah.amwool.regression

import com.github.mesayah.amwool.mlcommon.AbstractLearningTest
import weka.classifiers.Classifier
import weka.classifiers.functions.LinearRegression
import weka.core.Instances

class RegressionLearningTest : AbstractLearningTest() {
    override fun Instances.prepareData(): Instances = prepareDataForRegression()
    override val modelSupplier: () -> Classifier = { LinearRegression() }
    override val dataResourceFileName: String = "data.csv"
}