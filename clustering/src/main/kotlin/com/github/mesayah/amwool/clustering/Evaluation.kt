package com.github.mesayah.amwool.clustering

import com.github.mesayah.amwool.mlcommon.AbstractEvaluateCommand
import com.github.mesayah.amwool.mlcommon.ClustererEvaluate
import com.github.mesayah.amwool.mlcommon.Evaluate
import com.github.mesayah.amwool.mlcommon.PrepareData
import weka.clusterers.DensityBasedClusterer

object ClusteringEvaluateCommand : AbstractEvaluateCommand<DensityBasedClusterer, Double>() {
    override val evaluateTypeclass: Evaluate<DensityBasedClusterer, Double> = ClustererEvaluate()
    override val prepareDataTypeclass: PrepareData<DensityBasedClusterer> = PrepareDataForClustering()
}