package index

import analyzer.JapaneseAnalyzer
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * @author hakiba
 */
internal class IndexerTest {

    @Test
    fun indexerTest() {
        val analyzer = JapaneseAnalyzer()
        val storage = OnMemoryStorage()
        val indexer = Indexer(
            analyzer,
            NumberIdGenerator(),
            storage
        )
        val input = "<html>HTMLタグ</html>"
        val documentId = indexer.addDocument(input)
        indexer.flush()

        assertNotNull(documentId)
        val document = storage.document(documentId)!!
        assertEquals(document.text, input)
        assertIndex(storage, "html", documentId, 1)
        assertIndex(storage, "タグ", documentId, 1)
    }

    private fun assertIndex(storage: OnMemoryStorage, tokenId: String, documentId: String, usedCount: Int) {
        val index = storage.index(tokenId)!!
        assertTrue(index.postingList.contains(Posting(documentId, usedCount)))
    }
}