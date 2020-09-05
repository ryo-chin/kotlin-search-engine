package searcher

import analyzer.JapaneseAnalyzer
import index.Indexer
import index.NumberIdGenerator
import index.OnMemoryStorage
import org.junit.jupiter.api.Test

/**
 * @author hakiba
 */
internal class SearcherTest {
    private val storage = OnMemoryStorage()
    private val indexer = Indexer(JapaneseAnalyzer(), NumberIdGenerator(), storage)
    private val searcher = Searcher(JapaneseAnalyzer(), storage)

    @Test
    fun search() {
        indexer.addDocument("同時に今を危く院もあたかもその通用たんでもをあるていないには通知得たなけれが、始終にも見るないなうた。")
        indexer.addDocument("私は生涯さきほどその詐欺院という事の以上が致さますた。")
        indexer.addDocument("国家を耽りでしょのもはたしてその間でたといたたな。")
        indexer.flush()

        val res = searcher.search("同時に")

        println(res)
    }
}