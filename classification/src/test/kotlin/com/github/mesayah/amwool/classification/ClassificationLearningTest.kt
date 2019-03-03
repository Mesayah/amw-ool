package com.github.mesayah.amwool.classification

import com.github.mesayah.amwool.mlcommon.AbstractLearningTest
import com.github.mesayah.amwool.mlcommon.buildModel
import org.junit.Test
import weka.classifiers.Classifier
import weka.classifiers.trees.J48
import weka.core.Instances
import weka.core.converters.ArffLoader
import weka.core.converters.Loader
import java.io.IOException

class ClassificationLearningTest : AbstractLearningTest() {
    override val loaderSupplier: () -> Loader = { ArffLoader() }
    override val classifierSupplier: () -> Classifier = { J48() }
    override val dataResourceFileName: String = "zoo.arff"
    override fun Instances.prepareData(): Instances = prepareDataForClassification(10)

    @Test
    fun shouldReturnExistingIndicesWhenSelectedMostImportantAttributes() {
        val dataInstances = loadTestDataInstances()

        val mostImportantAttributeIndices = dataInstances.selectMostImportantAttributes()

        mostImportantAttributeIndices.forEach {
            assert(dataInstances.attribute(it.name()) != null)
        }
    }

    @Test
    fun shouldNotContainNameAttributeAfterRemovingIt() {
        val dataInstances = loadTestDataInstances()

        val dataWithRemovedNameAttribute = dataInstances.removeAnimalNameAttribute()

        assert(dataWithRemovedNameAttribute[0]
            .enumerateAttributes()
            .asSequence()
            .none { attribute -> attribute.name() == "animal" }
        )
    }

    @Test
    fun shouldBuildTreeWithoutErrorForExampleData() {
        val data = loadTestDataInstances().removeAnimalNameAttribute()
        data.setClassIndex(data.numAttributes() - 1)
        data.buildModel(classifierSupplier.invoke())
    }

    @Test
    fun shouldLoadedDataHaveProperFormatWhenLoadingIt() {
        val dataInstances = loadTestDataInstances()

        val aardvark = dataInstances[0]
        assert(aardvark.stringValue(0) == "aardvark")
        assert(aardvark.stringValue(1) == "true")
        assert(aardvark.stringValue(2) == "false")
        assert(aardvark.stringValue(3) == "false")
        assert(aardvark.stringValue(4) == "true")
        assert(aardvark.stringValue(5) == "false")
        assert(aardvark.stringValue(6) == "false")
        assert(aardvark.stringValue(7) == "true")
        assert(aardvark.stringValue(8) == "true")
        assert(aardvark.stringValue(9) == "true")
        assert(aardvark.stringValue(10) == "true")
        assert(aardvark.stringValue(11) == "false")
        assert(aardvark.stringValue(12) == "false")
        assert(aardvark.value(13) == 4.0)
        assert(aardvark.stringValue(14) == "false")
        assert(aardvark.stringValue(15) == "false")
        assert(aardvark.stringValue(16) == "true")
        assert(aardvark.stringValue(17) == "mammal")
    }

}