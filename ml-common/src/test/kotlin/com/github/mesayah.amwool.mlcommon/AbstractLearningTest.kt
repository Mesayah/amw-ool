package com.github.mesayah.amwool.mlcommon

import org.junit.Test
import weka.classifiers.Classifier
import weka.core.Instances
import weka.core.converters.Loader
import java.io.File

abstract class AbstractLearningTest {

    abstract val loader: () -> Loader
    abstract val classifier: () -> Classifier
    abstract val dataResourceFileName: String
    abstract fun Instances.prepareData(): Instances

    @Test
    fun shouldBuildModelWithoutException() {
        File(javaClass.classLoader.getResource(dataResourceFileName).toURI())
            .loadDataInstances(loader.invoke())
            .prepareData()
            .let {
                it.buildModel(classifier.invoke())
                    .evaluate(it)
            }
    }
}

