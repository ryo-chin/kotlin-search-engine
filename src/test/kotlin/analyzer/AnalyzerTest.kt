package analyzer

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


/**
 * @author hakiba
 */
internal class AnalyzerTest {

    @Test
    fun japaneseAnalyzerTest() {
        val analyzer = JapaneseAnalyzer()
        listOf(
            "<html>HTMLタグ</html>" to listOf(Token("html",  "名詞"), Token("タグ",  "名詞")),
            "今日はいい天気ですね" to listOf(Token("今日",  "名詞"), Token("いい",  "形容詞"), Token("天気",  "名詞")),
            "お名前は何といいますか？" to listOf(Token("名前",  "名詞"), Token("何",  "名詞"), Token("いい",  "動詞"))
        ).forEachIndexed { index, test ->
            val input = test.first
            val expected = test.second
            val actual = analyzer.analyze(input)
            assertEquals(expected, actual, "Case$index")
        }
    }

    @Test
    fun englishAnalyzerTest() {
        val analyzer = EnglishAnalyzer()
        listOf(
            "<html>HTML Tag</html>" to listOf(Token("html",  ""), Token("tag",  "")),
            "What is your name ?" to listOf(Token("what",  ""), Token("your",  ""), Token("name",  ""), Token("?",  "")),
        ).forEachIndexed { index, test ->
            val input = test.first
            val expected = test.second
            val actual = analyzer.analyze(input)
            assertEquals(expected, actual, "Case$index")
        }
    }
}