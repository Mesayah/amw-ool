package com.github.mesayah.amwool.classification

import com.github.mesayah.amwool.mlcommon.*
import org.junit.Test
import weka.classifiers.trees.J48

class ClassificationLearningTest : AbstractLearningTest<J48>() {
    override val prepareDataTypeclassSupplier: () -> PrepareData<J48> = { PrepareDataForTreeClassification() }
    override val preapareParametersSupplier: () -> Array<Any> = { arrayOf(0) }
    override val learnTypeclassSupplier: () -> Learn<J48> = { ClassifierLearn() }
    override val saveTypeclassSupplier: () -> Save<J48> = { Save() }
    override val modelSupplier: () -> J48 = { J48() }
    override val dataResourceFileName: String = "zoo.arff"

    @Test
    fun shouldReturnExistingIndicesWhenSelectedMostImportantAttributes() {
        val dataInstances = getTestDataFile().loadDataInstances()

        val mostImportantAttributeIndices = PrepareDataForTreeClassification().run {
            dataInstances.selectMostImportantAttributes()
        }

        mostImportantAttributeIndices.forEach {
            assert(dataInstances.attribute(it.name()) != null)
        }
    }

    @Test
    fun shouldNotContainNameAttributeAfterRemovingIt() {
        val dataInstances = getTestDataFile().loadDataInstances()

        val dataWithRemovedNameAttribute = PrepareDataForTreeClassification().run {
            dataInstances.removeAnimalNameAttribute()
        }

        assert(dataWithRemovedNameAttribute[0]
            .enumerateAttributes()
            .asSequence()
            .none { attribute -> attribute.name() == "animal" }
        )
    }

    @Test
    fun shouldBuildTreeWithoutErrorForExampleData() {
        val data = PrepareDataForTreeClassification().run {
            getTestDataFile().loadDataInstances().removeAnimalNameAttribute().apply {
                setClassIndex(numAttributes() - 1)
            }
        }

        ClassifierLearn<J48>().run {
            modelSupplier.invoke().buildModel(data)
        }
    }

    @Test
    fun shouldLoadedDataHaveProperFormatWhenLoadingIt() {
        val dataInstances = getTestDataFile().loadDataInstances()

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