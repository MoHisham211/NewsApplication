package mo.zain.newsapplication.model

import java.io.Serializable


data class NewsModel(
    var articles: List<Article>,
    val status: String,
    val totalResults: Int
):Serializable