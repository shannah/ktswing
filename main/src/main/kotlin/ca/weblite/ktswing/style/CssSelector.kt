package ca.weblite.ktswing.style

import java.awt.Component
import javax.swing.JComponent

class CssSelector: Selector {
    private val parent: Selector?
    private val localSelector: SelectorDescription;

    private class SelectorDescription {
        var id: String? = null
        var classes: List<String> = listOf()
        var glob: Boolean = false

        constructor(selector: String) {
            if (selector.startsWith("#")) {
                id = selector.substring(1)
                if (id!!.contains(".")) {
                    val classesStr = id!!.substring(id!!.indexOf('.')+1)
                    id = id!!.substring(0, id!!.indexOf('.'))
                    classes = classesStr.split(".")
                } else {
                    classes = listOf()
                }
            } else if (selector.startsWith(".")) {
                classes = selector.substring(1).split(".")
            } else {
                glob = true
            }
        }
    }

    constructor(selector: String) {
        val spacePos = selector.trim().lastIndexOf(' ')
        if (spacePos == -1) {
            parent = null
            localSelector = SelectorDescription(selector.trim())
        } else {
            parent = CssSelector(selector.trim().substring(0, spacePos).trim())
            localSelector = SelectorDescription(selector.trim().substring(spacePos).trim())
        }
    }
    override fun matches(target: Any): Boolean {
        if (target is Component) {
            val comp = target as Component
            if (localSelector.id != null) {
                return matchId(comp) && matchClass(comp) && matchParent(comp)
            } else if (comp is JComponent && localSelector.classes.isNotEmpty()) {
                return matchClass(comp) && matchParent(comp)
            } else {
                return matchParent(comp)
            }
        }

        return false
    }

    private fun matchParent(target: Any): Boolean {
        if (parent == null) {
            return true
        }
        if (target is Component) {
            val comp = target as Component
            if (parent != null) {
                if (comp.parent == null) {
                    return false
                }
                return parent.matches(comp.parent)
            }
        }

        return false
    }

    private fun matchId(comp: Component): Boolean {
        return comp.name == localSelector.id
    }

    private fun matchClass(comp: Component): Boolean {
        if (comp is JComponent) {
            val jcomp = comp as JComponent
            val cssClass = jcomp.getClientProperty("cssClasses");
            if (cssClass != null) {
                val cssClasses = cssClass.toString().split(" ")
                for (cssClass in localSelector.classes) {
                    if (!cssClasses.contains(cssClass)) {
                        return false
                    }
                }
                return true
            }
        }
        return false
    }
}