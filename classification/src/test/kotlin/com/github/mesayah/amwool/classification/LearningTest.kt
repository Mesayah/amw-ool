package com.github.mesayah.amwool.classification

import org.junit.Test
import weka.classifiers.trees.J48
import weka.core.SerializationHelper
import java.io.File
import java.io.IOException
import java.nio.file.Files

class LearningTest {

    @Test
    fun shouldReturnExistingIndicesWhenSelectedMostImportantAttributes() {
        val dataInstances = loadDataInstances(LearningTest::class.java.classLoader.getResource("zoo.arff").path)

        val mostImportantAttributeIndices = selectMostImportantAttributes(dataInstances)

        mostImportantAttributeIndices.forEach {
            assert(dataInstances.attribute(it.name()) != null)
        }
    }

    @Test
    fun shouldNotContainRemovedAttributeAfterRemoveFilterApplied() {
        val dataInstances = loadDataInstances(LearningTest::class.java.classLoader.getResource("zoo.arff").path)

        val dataWithRemovedNameAttribute = removeAnimalNameAttribute(dataInstances)

        assert(dataWithRemovedNameAttribute[0]
            .enumerateAttributes()
            .asSequence()
            .none { attribute -> attribute.name() == "animal" }
        )
    }

    @Test
    fun shouldBuildTreeWithoutErrorForExampleData() {
        val data = removeAnimalNameAttribute(loadDataInstances(this.javaClass.classLoader.getResource("zoo.arff").path))
        data.setClassIndex(data.numAttributes() - 1)
        buildTree(data)
    }

    @Test
    fun shouldLoadDataWhenDataFileExists() {
        val dataFilePath = LearningTest::class.java.classLoader.getResource("zoo.arff").path

        val dataInstances = loadDataInstances(dataFilePath)

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

    @Test(expected = IOException::class)
    fun shouldThrowExceptionWhenTryingToLoadNonExistingFile() {
        val fileName = "nonexistingfile.arff"

        loadDataInstances(fileName)
    }

    @Test
    fun shouldSaveTreeToFileWhenOutputFileProvided() {
        val data = loadDataInstances(this.javaClass.classLoader.getResource("zoo.arff").path)
        data.setClassIndex(data.numAttributes() - 1)
        val tree = buildTree(data)

        println(tree)
        val file = saveTree(tree, File("tmp_outputfile"))

        assert(Files.exists(file.toPath()))
        val treeFromOutput = SerializationHelper.read(file.path) as J48
        println(treeFromOutput)

        Files.delete(file.toPath())
        //assert(tree.equals(treeFromOutput))
    }
}