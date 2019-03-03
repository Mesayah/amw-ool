package com.github.mesayah.amwool.classification

import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import com.github.mesayah.amwool.mlcommon.AbstractLearnCommand
import weka.attributeSelection.AttributeSelection
import weka.attributeSelection.InfoGainAttributeEval
import weka.attributeSelection.Ranker
import weka.classifiers.Classifier
import weka.classifiers.trees.J48
import weka.core.Attribute
import weka.core.Instances
import weka.filters.Filter
import weka.filters.unsupervised.attribute.Remove


object LearnClassificationCommand : AbstractLearnCommand<J48>() {
    private val attributeLimit by option(
        names = *arrayOf("-a", "--attributes"),
        help = "Number of the most important attributes to use, 0 for no limit"
    ).int().default(0)

    override val model = J48()
    override fun Instances.prepareData() = prepareDataForClassification(attributeLimit)
}

fun Instances.prepareDataForClassification(attributeLimit: Int) = this.apply {
    setClassIndex(numAttributes() - 1)
    removeAnimalNameAttribute()
        .limitAttributes(selectMostImportantAttributes(), attributeLimit)
}

fun Instances.limitAttributes(
    mostImportantAttributes: List<Attribute>,
    attributeLimit: Int
): Instances = with(Remove()) {
    mostImportantAttributes
        .filter { attribute -> attribute != this@limitAttributes.classAttribute() }
        .drop(attributeLimit)
        .map(Attribute::index)
        .toIntArray()
        .let { setAttributeIndicesArray(it) }
    setInputFormat(this@limitAttributes)
    Filter.useFilter(this@limitAttributes, this)
}.apply { logger.info("Removed not important attributes, ${numAttributes()} left, counting class attribute") }

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
