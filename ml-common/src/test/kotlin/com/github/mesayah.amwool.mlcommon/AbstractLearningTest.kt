package com.github.mesayah.amwool.mlcommon

import org.junit.Test
import weka.classifiers.Classifier
import weka.classifiers.trees.J48
import weka.core.Instances
import weka.core.SerializationHelper
import weka.core.converters.ConverterUtils
import weka.core.converters.Loader
import java.io.File
import java.io.IOException
import java.nio.file.Files
import kotlin.test.assertNotNull

abstract class AbstractLearningTest {

    abstract val classifierSupplier: () -> Classifier
    abstract val dataResourceFileName: String
    abstract fun Instances.prepareData(): Instances

    @Test
    fun shouldBuildModelWithTestDataWithoutException() {
        getTestDataFile()
            .loadDataInstances()
            .prepareData()
            .buildModel(classifierSupplier.invoke())
    }

    private fun Instances.buildModel() = buildModel(classifierSupplier.invoke())

    @Test
    fun shouldFileExistsAfterSavingModel() {
        val model = getTestDataFile()
            .loadDataInstances()
            .prepareData()
            .buildModel()

        val file = model.saveTo(File("tmp_outputfile"))

        assert(Files.exists(file.toPath()))

        Files.delete(file.toPath())
    }

    @Test
    fun shouldFileContainModelAfterSavingIt() {
        val model = getTestDataFile()
            .loadDataInstances()
            .prepareData()
            .buildModel()
        val file = model.saveTo(File("tmp_outputfile"))
        val modelFromOutput = SerializationHelper.read(file.path)

        assert(model.toString() == modelFromOutput.toString())

        Files.delete(file.toPath())
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowIllegalArgumentExceptionExceptionWhenTryingToLoadWrongFormatFile() {
        File("nonexisting.file")
            .loadDataInstances()
    }

    @Test(expected = IOException::class)
    fun shouldThrowIOExceptionWhenTryingToLoadNonExistingFile() {
        File("nonexisting.arff")
            .loadDataInstances()
    }

    @Test
    fun shouldLoadDataWithoutExceptionWhenDataFileExists() {
        assert(Files.exists(getTestDataFile().toPath()))

        assertNotNull(getTestDataFile().loadDataInstances())
    }

    protected fun getTestDataFile(dataFileName: String = dataResourceFileName) =
        File(javaClass.classLoader.getResource(dataFileName).toURI())
}

