import io.ktor.application.*
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import modules.articles.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import io.ktor.features.ContentNegotiation
import io.ktor.gson.GsonConverter
import io.ktor.http.ContentType
import io.ktor.locations.Locations

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
            module = Application::main
    ).start(true)
}

fun Application.main() {
    install(ContentNegotiation) { register(ContentType.Application.Json, GsonConverter()) }
    install(Locations)
    routing {
        route("articles") {
            articles()
        }
    }
}