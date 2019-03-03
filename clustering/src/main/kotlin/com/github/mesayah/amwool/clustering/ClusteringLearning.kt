package com.github.mesayah.amwool.clustering

import com.github.mesayah.amwool.mlcommon.AbstractLearnCommand
import weka.clusterers.EM
import weka.core.Instances

fun main(args: Array<String>) = ClusteringLearningCommand().main(args)

class ClusteringLearningCommand : AbstractLearnCommand<EM>() {
    override fun Instances.prepareData(): Instances = this
    override val model: EM = EM()
}
