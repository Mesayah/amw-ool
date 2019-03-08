package com.github.mesayah.amwool.classification

import com.github.mesayah.amwool.mlcommon.AbstractEvaluateCommand
import com.github.mesayah.amwool.mlcommon.ClassifierEvaluate
import com.github.mesayah.amwool.mlcommon.Evaluate
import weka.classifiers.Evaluation
import weka.classifiers.trees.J48

object EvaluateTreeCommand : AbstractEvaluateCommand<J48, Evaluation>() {
    override val evaluateTypeclass: Evaluate<J48, Evaluation> = ClassifierEvaluate()
}