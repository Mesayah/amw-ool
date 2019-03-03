package com.github.mesayah.amwool.clustering

import com.github.mesayah.amwool.mlcommon.AbstractLearningTest
import weka.classifiers.Classifier
import weka.classifiers.functions.LinearRegression
import weka.clusterers.EM
import weka.core.Instances

class ClusteringLearningTest : AbstractLearningTest<EM>() {
    override val modelSupplier: () -> EM = { EM() }
    override val dataResourceFileName: String = "bank-data.arff"
    override fun Instances.prepareData(): Instances = this
}