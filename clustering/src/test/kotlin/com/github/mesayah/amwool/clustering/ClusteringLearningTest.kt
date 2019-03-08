package com.github.mesayah.amwool.clustering

import com.github.mesayah.amwool.mlcommon.AbstractLearningTest
import com.github.mesayah.amwool.mlcommon.Learn
import com.github.mesayah.amwool.mlcommon.Prepare
import com.github.mesayah.amwool.mlcommon.Save
import weka.classifiers.Classifier
import weka.classifiers.functions.LinearRegression
import weka.clusterers.EM
import weka.core.Instances

class ClusteringLearningTest : AbstractLearningTest<EM>() {
    override val prepareTypeclassSupplier: () -> Prepare<EM> = { PrepareForClustering() }
    override val learnTypeclassSupplier: () -> Learn<EM> = { LearnClusterer() }
    override val saveTypeclassSupplier: () -> Save<EM> = { Save() }
    override val preapareParametersSupplier: () -> Array<Any> = { emptyArray() }
    override val modelSupplier: () -> EM = { EM() }
    override val dataResourceFileName: String = "bank-data.arff"
}