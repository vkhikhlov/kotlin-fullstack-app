package modules.base

import components.panels.*
import vue.*
import vue.ext.jsObject
import vue.vdom.*
import kotlin.reflect.KClass

interface BaseModuleProps : VProps {
    var panels: Panels

    interface Panels {
        var left: Panel
        var right: Panel
        var top: Panel
        var bottom: Panel

        interface Panel {
            var menuItems: Array<Item>
            var visible: Boolean
        }
    }
}

interface BaseModuleComponent<out V : VData, out P : BaseModuleProps, out R : VRefs> : VueComponent<V, P, R>

open class BaseModule<
        out D : VData,
        out P : BaseModuleProps,
        out R : VRefs,
        out C : VComputed,
        out BC : BaseModuleComponent<D, P, R>>(kClass: KClass<out BC>) : VueOptions<D, P, R, C, BC>(kClass) {
    init {
        props {
            panels = jsObject { }
        }
    }

    override fun VBuilder.render() {
        div {
            v.bind.style {
                height = "100%"
                display = "flex"
                flexDirection = "column"
            }
            div("ui horizontal segments") {
                v.bind.style { height = "100%" }
                div("ui segment") {
                    tabPanel {
                        v.props = jsObject<TabPanelProps> {
                            type = TabPanelType.Horizontal(HorizontalDirection.LEFT)
                            items = mutableListOf(jsObject {
                                id = 1
                                title = "Title"
                                content = "Content"
                                type = MenuItemType.LEFT
                            })
                        }
                    }
                }
                div("ui segment") {
                    +"Middle"
                }
                div("ui segment") {
                    +"Right"
                }
            }
            div("ui segment") {
                +"Footer"
                v.bind.style { textAlign = "center" }
            }
        }
    }
}