import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.gson.GsonConverter
import io.ktor.http.ContentType
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils.create

data class City(val id: Int? = null, val name: String)

object Cities : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    var name = varchar("name", 50)
}

fun main(args: Array<String>) {
    Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
    transaction {
        logger.addLogger(StdOutSqlLogger)
        create(Cities)
        Cities.insert {
            it[name] = "St. Petersburg"
        } get Cities.id
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
        get("/") {
            val city = transaction {
                val row = Cities.selectAll().single()
                City(row[Cities.id], row[Cities.name])
            }
            call.respond(city)
        }
    }
}