package com.github.mesayah.amwool.clustering

import com.github.mesayah.amwool.mlcommon.AbstractLearnCommand
import com.github.mesayah.amwool.mlcommon.Learn
import com.github.mesayah.amwool.mlcommon.Prepare
import com.github.mesayah.amwool.mlcommon.Save
import weka.clusterers.EM
import weka.core.Instances

fun main(args: Array<String>) = ClusteringLearningCommand().main(args)

class ClusteringLearningCommand : AbstractLearnCommand<EM>() {
    override val prepareTypeclass: Prepare<EM> = PrepareForClustering()
    override val learnTypeclass: Learn<EM> = LearnClusterer()
    override val saveTypeclass: Save<EM> = Save()
    override val prepareParameters: Array<Any> = emptyArray()
    override val model: EM = EM()
}

class LearnClusterer : Learn<EM> {
    override fun EM.buildModel(data: Instances): EM = apply { buildClusterer(data) }
}

class PrepareForClustering : Prepare<EM> {
    override fun Instances.prepare(vararg parameters: Any): Instances = this
}