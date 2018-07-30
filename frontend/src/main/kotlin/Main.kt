import vue.*
import vue.router.VueRouter
import components.app.AppOptions

external fun require(name: String): dynamic

@Suppress("Unused")
fun main(args: Array<String>) {
    require("assets/semantic-ui/dist/semantic.js")
    require("assets/semantic-ui/dist/semantic.css")
    Vue.use(VueRouter, null)
    Vue(AppOptions).mount("#app")
}