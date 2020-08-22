package analyzer

import com.atilika.kuromoji.ipadic.Tokenizer

interface Tokenizer {
    fun tokenize(text: String): List<Token>
}

data class Token(
    val surface: String,
    val partOfSpeech: String
)

class KuromojiTokenizer: analyzer.Tokenizer {
    companion object {
        val tokenizer = Tokenizer()
    }

    override fun tokenize(text: String): List<Token> {
        return tokenizer.tokenize(text).map { Token(
            surface = it.surface,
            partOfSpeech = it.partOfSpeechLevel1 // ['動詞', '名詞', '助動詞', '副詞', '形容詞', ...]
        ) }
    }
}

class WhiteSpaceTokenizer: analyzer.Tokenizer {
    override fun tokenize(text: String): List<Token> {
        return text.split(" ").map { Token(it, "") }
    }
}

