import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.gson.GsonConverter
import io.ktor.http.ContentType
import io.ktor.locations.Locations
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import modules.articles.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>) {
    Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
    transaction {
        logger.addLogger(StdOutSqlLogger)
        initArticles()
        val hello = Word.new { name = "hello" }
        val world = Word.new { name = "world" }
        val art = Article.new { title = "hello world sample" }
        WordAndArticle.new {
            article = art
            word = hello
        }

        WordAndArticle.new {
            article = art
            word = world
        }
    }
    embeddedServer(
            Netty, watchPaths = listOf("backend"), port = 8080,
            module = Application::myModule
    ).start(true)
}

@location("/word/{wordId}/")
data class WordsLocation(val wordId: Int)

@location("/article/{articleId}/")
data class ArticlesLocation(val articleId: Int)

fun Application.myModule() {
    install(ContentNegotiation) {
        register(ContentType.Application.Json, GsonConverter())
    }
    install(Locations)
    routing {
        get<WordsLocation> { data ->
            val word = transaction {
                val word = Word.find { Words.id eq data.wordId }.firstOrNull()
                mapOf("name" to word?.name, "articles" to word?.articles?.map { mapOf("title" to it.title) })
            }
            call.respond(word)
        }
        get<ArticlesLocation> { data ->
            val article = transaction {
                val article = Article.find { Articles.id eq data.articleId }.firstOrNull()
                mapOf("title" to article?.title, "words" to article?.words?.map { mapOf("name" to it.name) })
            }
            call.respond(article)
        }
    }
}