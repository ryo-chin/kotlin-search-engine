package index

/**
 * @author hakiba
 */
data class Document(
    val documentId: String,
    val text: String,
    val tokenCount: Int
)

interface DocumentIdGenerator {
    fun generate(): String
}

class NumberIdGenerator : DocumentIdGenerator {
    companion object {
        private const val INITIAL_INDEX_ID = "0"
        private var lastId = INITIAL_INDEX_ID
    }

    override fun generate(): String {
        val nextId = lastId.toLong().plus(1).toString()
        return nextId.also { lastId = it }
    }
}