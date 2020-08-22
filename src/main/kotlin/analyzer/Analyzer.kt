package analyzer

interface Analyzer {
    fun analyze(text: String): List<Token>
}

abstract class AbstractAnalyzer(
    val tokenizer: Tokenizer,
    val charFilters: List<CharFilter> = emptyList(),
    val tokenFilters: List<TokenFilter> = emptyList(),
) : Analyzer {
    override fun analyze(text: String): List<Token> {
        val charFilteredText = charFilters.fold(text) { filtered, filter -> filter.filter(filtered) }
        val tokens = tokenizer.tokenize(charFilteredText)
        return tokens.map { token ->
            var filteredToken = token
            tokenFilters.forEach {
                filteredToken = it.filter(token) ?: return@map null
            }
            filteredToken
        }.filterNotNull()
    }
}

class JapaneseAnalyzer : AbstractAnalyzer(
    tokenizer = KuromojiTokenizer(),
    charFilters = listOf(HtmlStripFilter(), LowerCaseFilter()),
    tokenFilters = listOf(StopWordTokenFilter(), POSFilter(), Stemmer())
)

class EnglishAnalyzer : AbstractAnalyzer(
    tokenizer = WhiteSpaceTokenizer(),
    charFilters = listOf(HtmlStripFilter(), LowerCaseFilter()),
    tokenFilters = listOf(StopWordTokenFilter(), Stemmer())
)


