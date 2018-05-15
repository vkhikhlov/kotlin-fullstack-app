package modules.articles

import io.ktor.application.call
import io.ktor.locations.get
import io.ktor.locations.location
import io.ktor.response.respond
import io.ktor.routing.Route

@location("/word/{wordId}/")
data class WordsLocation(val wordId: Int)

@location("/article/{articleId}/")
data class ArticlesLocation(val articleId: Int)

fun Route.articles() {
    get<WordsLocation> { data -> call.respond(Api.Words.get(data)) }
    get<ArticlesLocation> { data -> call.respond(Api.Articles.get(data)) }
}