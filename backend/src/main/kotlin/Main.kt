import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*


fun main(args: Array<String>) {
    embeddedServer(
            Netty, watchPaths = listOf("backend"), port = 8080,
            module = Application::myModule
    ).start(true)
}

fun Application.myModule() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
}