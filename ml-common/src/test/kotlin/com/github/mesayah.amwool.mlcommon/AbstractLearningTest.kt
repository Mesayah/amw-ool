package com.github.mesayah.amwool.mlcommon

import org.junit.Test
import weka.classifiers.Classifier
import weka.classifiers.trees.J48
import weka.core.Instances
import weka.core.SerializationHelper
import weka.core.converters.Loader
import java.io.File
import java.io.IOException
import java.nio.file.Files

abstract class AbstractLearningTest {

    abstract val loaderSupplier: () -> Loader
    abstract val classifierSupplier: () -> Classifier
    abstract val dataResourceFileName: String
    abstract fun Instances.prepareData(): Instances

    @Test
    fun shouldBuildModelWithTestDataWithoutException() {
        loadTestDataInstances()
            .prepareData()
            .buildModel(classifierSupplier.invoke())
    }

    private fun Instances.buildModel() = buildModel(classifierSupplier.invoke())

    @Test
    fun shouldFileExistsAfterSavingModel() {
        val model = loadTestDataInstances()
            .buildModel()

        val file = model.saveTo(File("tmp_outputfile"))

        assert(Files.exists(file.toPath()))

        Files.delete(file.toPath())
    }

    @Test
    fun shouldFileContainModelAfterSavingIt() {
        val model = loadTestDataInstances()
            .buildModel()
        val file = model.saveTo(File("tmp_outputfile"))
        val modelFromOutput = SerializationHelper.read(file.path) as J48

        assert(model.toString() == modelFromOutput.toString())

        Files.delete(file.toPath())
    }

    @Test(expected = IOException::class)
    fun shouldThrowExceptionWhenTryingToLoadNonExistingFile() {
        getTestDataFile("nonexisting.file")
            .loadDataInstances(loaderSupplier.invoke())
    }

    @Test
    fun shouldLoadDataWithoutExceptionWhenDataFileExists() {
        assert(Files.exists(getTestDataFile().toPath()))

        loadTestDataInstances()
    }

    protected fun loadTestDataInstances(): Instances = getTestDataFile().loadDataInstances(loaderSupplier.invoke())

    protected fun getTestDataFile(dataFileName: String = dataResourceFileName) = File(javaClass.classLoader.getResource(dataFileName).toURI())
}

