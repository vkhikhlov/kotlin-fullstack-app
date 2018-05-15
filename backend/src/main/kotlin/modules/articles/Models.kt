package modules.articles

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.SchemaUtils.create

object Articles : IntIdTable() {
    var title = varchar("name", 200)
}

object Words : IntIdTable() {
    var name = varchar("name", 50)
}

object WordsAndArticles : IntIdTable() {
    val word = reference("word", Words)
    val article = reference("article", Articles)
}

class Article(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Article>(Articles)

    var title by Articles.title
    val words get() = _words.map { it.word }
    private val _words by WordAndArticle referrersOn WordsAndArticles.article
}

class Word(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Word>(Words)

    var name by Words.name
    val articles get() = _articles.map { it.article }
    private val _articles by WordAndArticle referrersOn WordsAndArticles.word
}

class WordAndArticle(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<WordAndArticle>(WordsAndArticles)

    var word by Word referencedOn WordsAndArticles.word
    var article by Article referencedOn WordsAndArticles.article
}

fun Transaction.initArticles() = let { create(Articles, Words, WordsAndArticles) }