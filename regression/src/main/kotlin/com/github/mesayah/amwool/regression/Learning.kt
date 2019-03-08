package com.github.mesayah.amwool.regression

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.mesayah.amwool.mlcommon.*
import weka.classifiers.functions.LinearRegression
import weka.core.Instances

fun main(args: Array<String>) = object : CliktCommand() {
    override fun run() = Unit
}.subcommands(RegressionLearnCommand, RegressionEvaluateCommand).main(args)

object RegressionLearnCommand : AbstractLearnCommand<LinearRegression>() {
    override val prepareDataTypeclass: PrepareData<LinearRegression> = PrepareDataForLinearRegression()
    override val learnTypeclass: Learn<LinearRegression> = ClassifierLearn()
    override val saveTypeclass: Save<LinearRegression> = Save()
    override val model: LinearRegression = LinearRegression()
}

class PrepareDataForLinearRegression :
    PrepareData<LinearRegression> {
    override fun Instances.prepare(): Instances = this.apply {
        setClassIndex(numAttributes() - 2)
        deleteAttributeAt(numAttributes() - 1)
    }
}