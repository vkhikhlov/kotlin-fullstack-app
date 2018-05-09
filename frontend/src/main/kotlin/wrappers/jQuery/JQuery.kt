package wrappers.jQuery

import org.w3c.dom.Element

@JsName("jQuery")
external fun jq(el: Element): JQuery

@JsName("jQuery")
external fun jq(selector: String): JQuery

external interface JQuery {
    fun tab()
}