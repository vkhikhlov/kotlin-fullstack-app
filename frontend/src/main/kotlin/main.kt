import components.panels.*
import vue.*
import vue.ext.jsObject
import vue.vdom.*

external fun require(name: String): dynamic
interface MainComponent : VueComponent<VData, VProps, VRefs>

@Suppress("Unused")
fun main(args: Array<String>) {
    require("assets/semantic-ui/dist/semantic.js")
    require("assets/semantic-ui/dist/semantic.css")
    val vm = Vue(object : VueOptions<VData, VProps, VRefs, VComputed, MainComponent>(MainComponent::class) {
        override fun Template.render() = root {
            div {
                +"Hello World!"
                tabPanel {
                    type = TabPanelType.Horizontal(HorizontalDirection.RIGHT)
                    items = mutableListOf()
                    for (i in 1..4) {
                        items.add(jsObject {
                            id = i; title = "title$i"; content = "Content$i"
                            type = if (i % 2 == 0) MenuItemType.LEFT else MenuItemType.RIGHT
                        })
                    }
                }
            }

        }
    })
    console.log(vm)
    vm.mount("#app")
}