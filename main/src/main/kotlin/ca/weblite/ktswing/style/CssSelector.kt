package ca.weblite.ktswing.style

import java.awt.Component
import javax.swing.JComponent

class CssSelector: Selector {
    private val parent: Selector?
    private val localSelector: SelectorDescription;
    private val recursiveParent: Boolean;

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
            recursiveParent = true
        } else {
            val parentString = selector.trim().substring(0, spacePos).trim()
            if (parentString.endsWith(">")) {
                parent = CssSelector(parentString.substring(0, parentString.length-1).trim())
                recursiveParent = false
            } else {
                parent = CssSelector(parentString)
                recursiveParent = true
            }
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
            if (comp.parent == null) {
                return false
            }

            val parentMatch =  parent.matches(comp.parent)
            if (parentMatch) {
                return true
            }

            if (recursiveParent && comp.parent.parent != null) {
                return parent.matches(comp.parent.parent)
            }

            return false
        }

        return false
    }

    private fun matchId(comp: Component): Boolean {
        if (localSelector.id == null) {
            return true
        }
        return comp.name == localSelector.id
    }

    private fun matchClass(comp: Component): Boolean {
        if (localSelector.classes.isEmpty()) {
            return true
        }
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