package com.github.mesayah.amwool.mlcommon

import org.junit.Test
import weka.classifiers.Classifier
import weka.core.Instances
import weka.core.SerializationHelper
import java.io.File
import java.io.IOException
import java.nio.file.Files
import kotlin.test.assertNotNull

abstract class AbstractLearningTest<T> {

    abstract val modelSupplier: () -> T
    abstract val dataResourceFileName: String
    abstract fun Instances.prepareData(): Instances

    @Test
    fun shouldBuildModelWithTestDataWithoutException() {
        getTestDataFile()
            .loadDataInstances()
            .prepareData().let {
                modelSupplier.invoke().buildModel(it)
            }
    }


    @Test
    fun shouldFileExistsAfterSavingModel() {
        val model = getTestDataFile()
            .loadDataInstances()
            .prepareData().let {
                modelSupplier.invoke().buildModel(it)
            }

        val file = model.saveTo(File("tmp_outputfile"))

        assert(Files.exists(file.toPath()))

        Files.delete(file.toPath())
    }

    @Test
    fun shouldFileContainModelAfterSavingIt() {
        val model = getTestDataFile()
            .loadDataInstances()
            .prepareData().let {
                modelSupplier.invoke().buildModel(it)
            }
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

