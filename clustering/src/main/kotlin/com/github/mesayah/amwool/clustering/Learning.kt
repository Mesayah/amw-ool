package com.github.mesayah.amwool.clustering

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.mesayah.amwool.mlcommon.*
import weka.clusterers.Clusterer
import weka.clusterers.DensityBasedClusterer
import weka.clusterers.EM
import weka.core.Instances

fun main(args: Array<String>) = object : CliktCommand() {
    override fun run() = Unit
}.subcommands(ClusteringLearningCommand, ClusteringEvaluateCommand).main(args)

object ClusteringLearningCommand : AbstractLearnCommand<DensityBasedClusterer>() {
    override val prepareDataTypeclass: PrepareData<DensityBasedClusterer> = PrepareDataForClustering()
    override val learnTypeclass: Learn<DensityBasedClusterer> = ClustererLearn()
    override val saveTypeclass: Save<DensityBasedClusterer> = Save()
    override val model: EM = EM()
}

class PrepareDataForClustering : PrepareData<DensityBasedClusterer> {
    override fun Instances.prepare(): Instances = this
}