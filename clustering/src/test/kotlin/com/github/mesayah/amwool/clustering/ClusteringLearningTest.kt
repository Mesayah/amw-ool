package com.github.mesayah.amwool.clustering

import com.github.mesayah.amwool.mlcommon.*
import weka.clusterers.DensityBasedClusterer
import weka.clusterers.EM

class ClusteringLearningTest : AbstractLearningTest<DensityBasedClusterer>() {
    override val prepareDataTypeclassSupplier: () -> PrepareData<DensityBasedClusterer> = { PrepareDataForClustering() }
    override val learnTypeclassSupplier: () -> Learn<DensityBasedClusterer> = { ClustererLearn() }
    override val saveTypeclassSupplier: () -> Save<DensityBasedClusterer> = { Save() }
    override val preapareParametersSupplier: () -> Array<Any> = { emptyArray() }
    override val modelSupplier: () -> DensityBasedClusterer = { EM() }
    override val dataResourceFileName: String = "bank-data.arff"
}