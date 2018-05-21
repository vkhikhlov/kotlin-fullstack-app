package components.app

import kotlinx.html.DIV
import vue.*
import vue.ext.jsObject
import vue.vdom.*
import vue.router.*

interface AppComponent : VueComponent<VData, VProps, VRefs>

object AppOptions : VueOptions<VData, VProps, VRefs, VComputed, AppComponent>(AppComponent::class) {
    override fun VBuilder.render() = div {
        v.bind.style {
            height = "100%"
            display = "flex"
            flexDirection = "column"
        }
        div("ui secondary pointing menu") {
            routerLink {
                +"Articles"
                v.bind.clazz { "active item" to true }
                v.props = jsObject<RouterLinkProps> {
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
}

fun VBuilder.app(block: VBuilder.() -> Unit = {}) = child(AppOptions, block)