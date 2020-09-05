package searcher

import analyzer.Analyzer
import index.Document
import index.IndexStorage

/**
 * @author hakiba
 */
class Searcher(
    private val analyzer: Analyzer,
    private val storage: IndexStorage,
) {
    fun search(input: String): List<Document>{
        // 入力値をトークンに変換
        val tokens = analyzer.analyze(input)

        // Indexをトークンで検索
        val targetIndex = tokens.mapNotNull { storage.index(it.tokenId()) }
        val postings = targetIndex.map { it.postingList }.flatten()
        val documents = postings.map { it.documentId }.mapNotNull { storage.document(it) }

        // スコアに応じてソート

        return documents
    }
}