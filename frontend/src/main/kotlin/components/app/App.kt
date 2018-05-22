package components.app

import vue.*
import vue.ext.*
import vue.vdom.*
import vue.router.*

interface AppComponent : VueComponent<VData, VProps, VRefs>

fun VBuilder.appTemplate() = div {
    v.bind.style {
        height = "100%"
        display = "flex"
        flexDirection = "column"
    }
    div("ui secondary pointing menu") {
        routerLink {
            +"Articles"
            v.bind.clazz { "active item" to true }
            v.props = jsObject {
                to = "/articles"
                activeClass = "selected"
            }
        }
    }
    div {
        v.bind.style { height = "100%" }
        routerView()
    }
}

object AppOptions : VueOptions<VData, VProps, VRefs, VComputed, AppComponent>(AppComponent::class) {
    override fun VBuilder.render() = appTemplate()
}

fun VBuilder.app(block: VBuilder.() -> Unit = {}) = child(AppOptions, block)