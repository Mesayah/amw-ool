package pl.mesayah.amwool.webscraping

import org.jsoup.Jsoup
import org.jsoup.nodes.Element

data class Article(
    val title: Element,
    val content: List<Element>
) {
    override fun toString(): String =
        title.text() + "\n\n" +
                content.map { it.text() }
                    .reduce { acc, element -> acc + "\n\n" + element }

    companion object {
        const val TITLE_ID = "firstHeading"
        const val CONTENT_ID = "bodyContent"

        private val HEADER_TAGS = (1..6).map { "h$it" }.toSet()

        fun from(url: String): Article {
            val document = Jsoup.connect(url).get()
            val title = document.getElementById(TITLE_ID)
            val content = document
                .getElementById("mw-content-text")
                .children()
                .first()
                .children()
                .filter {
                    HEADER_TAGS.contains(it.tagName()) ||
                            it.tagName() == "p"
                }

            return Article(title, content)
        }

    }
}

fun main(args: Array<String>) {
    if (!args.isEmpty()) {
        val keyword = args.joinToString(" ")
        print(Article.from("http://pl.wikipedia.org/wiki/$keyword"))
    }
}