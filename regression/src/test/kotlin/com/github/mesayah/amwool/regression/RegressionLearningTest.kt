package com.github.mesayah.amwool.regression

import com.github.mesayah.amwool.mlcommon.*
import weka.classifiers.functions.LinearRegression

class RegressionLearningTest : AbstractLearningTest<LinearRegression>() {
    override val prepareTypeclassSupplier: () -> Prepare<LinearRegression> = { PrepareForLinearRegression() }
    override val learnTypeclassSupplier: () -> Learn<LinearRegression> = { ClassifierLearn() }
    override val saveTypeclassSupplier: () -> Save<LinearRegression> = { Save() }
    override val preapareParametersSupplier: () -> Array<Any> = { emptyArray() }
    override val modelSupplier: () -> LinearRegression = { LinearRegression() }
    override val dataResourceFileName: String = "data.csv"
}