package analyzer

import com.atilika.kuromoji.ipadic.Tokenizer

interface Tokenizer {
    fun tokenize(text: String): List<Token>
}

data class Token(
    val surface: String,
    val partOfSpeech: String
) {
    fun tokenId(): String {
        return surface
    }
}

data class TokenCountResult(
    val result: Map<Token, Int>
) {
    fun mostCommon(): Map<Token, Int> {
        return result
    }
}

fun List<Token>.countUseCount(): TokenCountResult {
    return TokenCountResult(
        fold(mutableMapOf()) { acc, token ->
            acc.apply {
                val value = acc.getOrDefault(token, 0) + 1
                acc[token] = value
            }
        }
    )
}

class KuromojiTokenizer : analyzer.Tokenizer {
    companion object {
        val tokenizer = Tokenizer()
    }

    override fun tokenize(text: String): List<Token> {
        return tokenizer.tokenize(text).map {
            Token(
                surface = it.surface,
                partOfSpeech = it.partOfSpeechLevel1 // ['動詞', '名詞', '助動詞', '副詞', '形容詞', ...]
            )
        }
    }
}

class WhiteSpaceTokenizer : analyzer.Tokenizer {
    override fun tokenize(text: String): List<Token> {
        return text.split(" ").map { Token(it, "") }
    }
}

