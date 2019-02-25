package com.github.mesayah.amwool.classification

import org.junit.Test

class SelectAttributesTest {
    @Test
    fun shouldReturnExistingIndicesWhenSelectedMostImportantAttributes() {
        val dataInstances = loadDataInstances(LoadDataTest::class.java.classLoader.getResource("zoo.arff").path)

        val mostImportantAttributeIndices = selectMostImportantAttributes(dataInstances)

        mostImportantAttributeIndices.forEach {
            assert(dataInstances.attribute(it) != null)
        }
    }
}