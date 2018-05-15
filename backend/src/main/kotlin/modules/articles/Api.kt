package modules.articles

import org.jetbrains.exposed.sql.transactions.transaction

object Api {
    object Articles {
        fun get(data: ArticlesLocation) = transaction {
            val article = Article.find { modules.articles.Articles.id eq data.articleId }.firstOrNull()
            mapOf("title" to article?.title, "words" to article?.words?.map { mapOf("name" to it.name) })
        }
    }

    object Words {
        fun get(data: WordsLocation) = transaction {
            val word = Word.find { modules.articles.Words.id eq data.wordId }.firstOrNull()
            mapOf("name" to word?.name, "articles" to word?.articles?.map { mapOf("title" to it.title) })
        }
    }
}