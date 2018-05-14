import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.gson.GsonConverter
import io.ktor.http.ContentType
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

fun Application.myModule() {
    install(ContentNegotiation) {
        register(ContentType.Application.Json, GsonConverter())
    }
    routing {
        get("/words") {
            val words = transaction { Article.all().first().words.map { Words.Word(it.name) } }
            call.respond(words)
        }
        get("/articles") {
            val articles = transaction { Word.all().first().articles.map { Articles.Article(it.title) } }
            call.respond(articles)
        }
    }
}