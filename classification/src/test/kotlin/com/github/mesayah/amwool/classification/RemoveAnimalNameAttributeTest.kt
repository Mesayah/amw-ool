package com.github.mesayah.amwool.classification

import org.junit.Test

class RemoveAnimalNameAttributeTest {
    @Test
    fun shouldNotContainRemovedAttributeAfterRemoveFilterApplied() {
        val dataInstances = loadDataInstances(LoadDataTest::class.java.classLoader.getResource("zoo.arff").path)

        val dataWithRemovedNameAttribute = removeAnimalNameAttribute(dataInstances)

        assert(dataWithRemovedNameAttribute[0]
            .enumerateAttributes()
            .asSequence()
            .none { attribute -> attribute.name() == "animal" }
        )
    }
}