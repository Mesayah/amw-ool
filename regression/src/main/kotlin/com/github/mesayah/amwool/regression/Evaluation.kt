package com.github.mesayah.amwool.regression

import com.github.mesayah.amwool.mlcommon.AbstractEvaluateCommand
import com.github.mesayah.amwool.mlcommon.ClassifierEvaluate
import com.github.mesayah.amwool.mlcommon.Evaluate
import com.github.mesayah.amwool.mlcommon.PrepareData
import weka.classifiers.Evaluation
import weka.classifiers.functions.LinearRegression

object RegressionEvaluateCommand : AbstractEvaluateCommand<LinearRegression, Evaluation>() {
    override val evaluateTypeclass: Evaluate<LinearRegression, Evaluation> = ClassifierEvaluate()
    override val prepareDataTypeclass: PrepareData<LinearRegression> = PrepareDataForLinearRegression()
}