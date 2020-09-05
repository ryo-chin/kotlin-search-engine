package analyzer

import opennlp.tools.stemmer.PorterStemmer

interface TokenFilter {
    fun filter(token: Token): Token?
}

class StopWordTokenFilter: TokenFilter {
    companion object {
        val STOP_WORDS = listOf("is", "was", "to", "the")
    }

    override fun filter(token: Token): Token? {
        return token.takeUnless { STOP_WORDS.contains(token.surface) }
    }
}

class Stemmer: TokenFilter {
    companion object {
        val stemmer = PorterStemmer()
    }
    override fun filter(token: Token): Token? {
        return Token(
            surface = stemmer.stem(token.surface),
            partOfSpeech = token.partOfSpeech
        )
    }
}

class POSFilter: TokenFilter {
    companion object {
        val STOP_POS_LIST = listOf("助詞", "助動詞", "記号", "接頭詞")
    }
    override fun filter(token: Token): Token? {
        return token.takeUnless { STOP_POS_LIST.contains(token.partOfSpeech) }
    }
}
