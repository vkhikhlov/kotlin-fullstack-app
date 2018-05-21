import components.app.app
import modules.articles.ArticleOptions
import vue.*
import vue.ext.jsObject
import vue.router.Router
import vue.router.VueRouter

external fun require(name: String): dynamic
interface MainComponent : VueComponent<VData, VProps, VRefs>

@Suppress("Unused")
fun main(args: Array<String>) {
    require("assets/semantic-ui/dist/semantic.js")
    require("assets/semantic-ui/dist/semantic.css")
    Vue.use(VueRouter, null)
    val vm = Vue(object : VueOptions<VData, VProps, VRefs, VComputed, MainComponent>(MainComponent::class) {
        init {
            router = Router(jsObject {
                routes = arrayOf(jsObject {
                    path = "/articles"
                    component = ArticleOptions
                })
                mode = "history"
            })
        }
        override fun VBuilder.render() = app()
    })
    vm.mount("#app")
}