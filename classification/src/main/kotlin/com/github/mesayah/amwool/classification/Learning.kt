package com.github.mesayah.amwool.classification

import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import com.github.mesayah.amwool.mlcommon.*
import weka.attributeSelection.AttributeSelection
import weka.attributeSelection.InfoGainAttributeEval
import weka.attributeSelection.Ranker
import weka.classifiers.trees.J48
import weka.core.Attribute
import weka.core.Instances
import weka.filters.Filter
import weka.filters.unsupervised.attribute.Remove

object LearnTreeCommand : AbstractLearnCommand<J48>() {
    private val attributeLimit by option(
        names = *arrayOf("-a", "--attributes"),
        help = "Number of the most important attributes to use, 0 for no limit"
    ).int().default(0)

    override val prepareTypeclass: Prepare<J48> = PrepareForTree()

    override val learnTypeclass: Learn<J48> = ClassifierLearn()
    override val saveTypeclass: Save<J48> = Save()
    override val model = J48()
    override val prepareParameters: Array<Any> by lazy { arrayOf(attributeLimit as Any) }
}

class PrepareForTree : Prepare<J48> {
    override fun Instances.prepare(vararg parameters: Any): Instances = this.apply {
        setClassIndex(numAttributes() - 1)
        removeAnimalNameAttribute()
            .limitAttributes(selectMostImportantAttributes(), parameters[0] as Int)
    }

    private fun Instances.limitAttributes(
        mostImportantAttributes: List<Attribute>,
        attributeLimit: Int
    ): Instances = if (attributeLimit == 0) this else {
        with(Remove()) {
            mostImportantAttributes
                .filter { attribute -> attribute != this@limitAttributes.classAttribute() }
                .drop(attributeLimit)
                .map(Attribute::index)
                .toIntArray()
                .let { setAttributeIndicesArray(it) }
            setInputFormat(this@limitAttributes)
            Filter.useFilter(this@limitAttributes, this)
        }.apply { logger.info("Removed not important attributes, ${numAttributes()} left, counting class attribute") }
    }

    fun Instances.selectMostImportantAttributes(): List<Attribute> = with(AttributeSelection()) {
        apply {
            setEvaluator(InfoGainAttributeEval())
            setSearch(Ranker())
            SelectAttributes(this@selectMostImportantAttributes)
        }
        selectedAttributes()
            .map { this@selectMostImportantAttributes.attribute(it) }
            .apply { logger.info("Selected the most important attributes: $this") }
    }

    fun Instances.removeAnimalNameAttribute(): Instances = this.apply {
        deleteAttributeAt(0)
    }

}

