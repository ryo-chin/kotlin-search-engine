package index

interface IndexStorage {
    fun saveDocument(documentId: String, document: Document)
    fun saveIndex(index: InvertedIndex)
    fun document(documentId: String): Document?
    fun index(tokenId: String): InvertedIndex?
}

class OnMemoryStorage(
    private val documents: MutableMap<String, Document> = mutableMapOf(),
    private val indexies: MutableMap<String, InvertedIndex> = mutableMapOf(),
) : IndexStorage {
    override fun saveDocument(documentId: String, document: Document) {
        documents[documentId] = document
    }

    override fun saveIndex(index: InvertedIndex) {
        indexies[index.tokenId] = index
    }

    override fun document(documentId: String): Document? {
        return documents[documentId]
    }

    override fun index(tokenId: String): InvertedIndex? {
        return indexies[tokenId]
    }
}
