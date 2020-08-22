package analyzer

interface CharFilter {
    fun filter(text: String): String
}

class HtmlStripFilter : CharFilter {
    companion object {
        val regex = Regex(pattern = "<[^>]*?>")
    }

    override fun filter(text: String): String {
        return regex.replace(text, "")
    }
}

class LowerCaseFilter : CharFilter {
    override fun filter(text: String): String {
        return text.toLowerCase()
    }
}
