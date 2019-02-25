package com.github.mesayah.amwool.classification

import org.junit.Test
import java.io.IOException

class LoadDataTest {

    @Test
    fun shouldLoadDataWhenDataFileExists() {
        val dataFilePath = LoadDataTest::class.java.classLoader.getResource("zoo.arff").path

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
}