package com.github.mesayah.amwool.classification

import org.junit.Test

class BuildTreeTest {
    @Test
    fun shouldBuildTreeWithoutErrorForExampleData() {
        val data = removeAnimalNameAttribute(loadDataInstances(BuildTreeTest::class.java.classLoader.getResource("zoo.arff").path))
        data.setClassIndex(data.numAttributes() - 1)
        buildTree(data)
    }
}