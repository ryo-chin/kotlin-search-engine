package index

import analyzer.Token

data class InvertedIndex(
    val tokenId: String,
    val token: Token,
    val postingList: List<Posting> = emptyList()
) {
    fun addPosting(documentId: String, useCount: Int): InvertedIndex {
        return InvertedIndex(
            tokenId,
            token,
            postingList + Posting(documentId, useCount)
        )
    }
}

data class Posting(
    val documentId: String,
    val useCount: Int
)