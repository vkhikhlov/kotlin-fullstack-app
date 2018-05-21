package components.panels

import kotlinx.html.DIV
import vue.*
import vue.vdom.*
import vue.ext.jsObject
import wrappers.jQuery.jq

enum class VerticalDirection { BOTTOM, TOP }
enum class HorizontalDirection { LEFT, RIGHT }
enum class MenuItemType { LEFT, RIGHT }

sealed class TabPanelType {
    data class Vertical(val direction: VerticalDirection) : TabPanelType()
    data class Horizontal(val direction: HorizontalDirection) : TabPanelType()
}

interface Item {
    var id: Int
    var content: String
    var title: String
    var type: MenuItemType
}

interface TabPanelProps : VProps {
    var type: TabPanelType
    var items: MutableList<Item>
}

interface TabPanelComponent : VueComponent<VData, TabPanelProps, VRefs>

object TabPanelOptions : VueOptions<VData, TabPanelProps, VRefs, VComputed, TabPanelComponent>(TabPanelComponent::class) {
    init {
        props {
            type = jsObject { }
            items = jsObject { }
        }
    }

    override fun onMounted() {
        jq(".menu .item").tab()
    }

    override fun VBuilder.render() = div {
        v.bind.style {
            display = "flex"
            flexDirection = if (props.type is HorizontalDirection) "column" else "row"
            height = "100%"
        }
        fun VDOMBuilder<DIV>.content(classes: String = "") = div("ui $classes segment") {
            v.bind.style {
                width = "100%"
                height = "100%"
            }
            +"This is an segment."
            for (item in props.items) {
                div("ui tab") { attrs.attributes["data-tab"] = "tab${item.id}"; +item.content }
            }
        }

        inline fun VDOMBuilder<DIV>.menuItems(fn: VDOMBuilder<DIV>.(Item) -> Any) {
            div("left menu") { props.items.filter { it.type == MenuItemType.LEFT }.forEach { fn(it) } }
            div("right menu") { props.items.filter { it.type == MenuItemType.RIGHT }.forEach { fn(it) } }
        }

        fun VDOMBuilder<DIV>.horizontalMenu(direction: HorizontalDirection) = div("ui horizontal segments") {
            fun VDOMBuilder<DIV>.menu() = div("ui compact segment") {
                v.bind.style {
                    padding = "0"
                    width = "25px !important"
                    if (direction == HorizontalDirection.RIGHT) transform = "rotate(-180deg)"
                    border = "0"
                }
                div("ui vertical fluid secondary pointing tabular menu") {
                    menuItems {
                        a(classes = "item") {
                            v.bind.style {
                                borderTop = "none"
                                borderLeft = "none"
                                padding = "4px"
                            }
                            attrs.attributes["data-tab"] = "tab${it.id}"
                            a { +it.title; v.bind.style { writingMode = "vertical-lr" } }
                        }
                    }
                }
            }
            if (direction == HorizontalDirection.LEFT) {
                menu()
                content()
            } else {
                content()
                menu()
            }
        }

        fun VDOMBuilder<DIV>.verticalMenu(direction: VerticalDirection) {
            fun VDOMBuilder<DIV>.menu() = div("ui attached fluid secondary pointing tabular menu") {
                v.bind.style {
                    if (direction == VerticalDirection.BOTTOM) transform = "rotate(-180deg)"
                    width = "100% !important"
                }
                menuItems {
                    a(classes = "item") {
                        v.bind.style { padding = "4px" }
                        attrs.attributes["data-tab"] = "tab${it.id}"
                        a {
                            +it.title
                            if (direction == VerticalDirection.BOTTOM) v.bind.style { transform = "rotate(180deg)" }
                        }
                    }
                }
            }
            if (direction == VerticalDirection.TOP) {
                menu()
                content("attached segment")
            } else {
                content("top attached segment")
                menu()
            }
        }

        val type = props.type
        when (type) {
            is TabPanelType.Horizontal -> horizontalMenu(type.direction)
            is TabPanelType.Vertical -> verticalMenu(type.direction)
        }
    }
}

fun VDOMBuilder<DIV>.tabPanel(block: VBuilder.() -> Unit = { }) = child(TabPanelOptions, block)