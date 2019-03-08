package com.github.mesayah.amwool.regression

import com.github.mesayah.amwool.mlcommon.*
import weka.classifiers.functions.LinearRegression
import weka.core.Instances

fun main(args: Array<String>) = RegressionLearningCommand().main(args)

class RegressionLearningCommand : AbstractLearnCommand<LinearRegression>() {
    override val prepareTypeclass: Prepare<LinearRegression> = PrepareForLinearRegression()
    override val learnTypeclass: Learn<LinearRegression> = ClassifierLearn()
    override val saveTypeclass: Save<LinearRegression> = Save()
    override val model: LinearRegression = LinearRegression()
    override val prepareParameters: Array<Any> = emptyArray()
}

class PrepareForLinearRegression :
    Prepare<LinearRegression> {
    override fun Instances.prepare(vararg parameters: Any): Instances = this.apply {
        setClassIndex(numAttributes() - 2)
        deleteAttributeAt(numAttributes() - 1)
    }
}