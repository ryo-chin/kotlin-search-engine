package index

import analyzer.Analyzer
import analyzer.countUseCount

/**
 * @author hakiba
 */
class Indexer(
    private val analyzer: Analyzer,
    private val idGenerator: DocumentIdGenerator,
    private val storage: IndexStorage
) {
    companion object {
        const val LIMIT = 1000
    }

    private var tempInvertedIndexes: MutableMap<String, InvertedIndex> = mutableMapOf()

    fun addDocument(text: String): String? {
        if (text.isBlank()) {
            return null
        }
        val tokens = analyzer.analyze(text)
        val documentId = idGenerator.generate()
        storage.saveDocument(documentId, Document(documentId, text, tokens.size))
        tokens.countUseCount().mostCommon().forEach {
            val tokenId: String = it.key.tokenId()
            val index = tempInvertedIndexes.getOrDefault(tokenId, InvertedIndex(tokenId, it.key))
            tempInvertedIndexes[tokenId] = index.addPosting(documentId, it.value)
        }

        if (tempInvertedIndexes.size > LIMIT) {
            flush()
        }

        return documentId
    }

    fun flush() {
        tempInvertedIndexes.values.forEach { storage.saveIndex(it) }
        tempInvertedIndexes = mutableMapOf()
    }
}
