package ca.weblite.ktswing.style

import java.awt.Component
import java.awt.Container

class Stylesheet {

    private val styles = mutableListOf<Style<out Component>>()

    fun <T: Component> register(style: Style<T>) {
        @Suppress("UNCHECKED_CAST")
        styles.add(style as Style<out Component>)
    }

    fun <T: Component> register(selectorString: String, targetClass: Class<T>, apply: (T) -> Unit) {
        register(object : AbstractStyle<T>(targetClass, CssSelector(selectorString)) {
            override fun apply(target: T) {
                apply(target)
            }
        })
    }

    fun <T: Component> register(targetClass: Class<T>, apply: (T) -> Unit) {
        register("*", targetClass, apply)
    }

    fun apply(root: Component) {
        if (root is Container) {
            val container = root as Container
            for (i in 0 until container.getComponentCount()) {
                apply(container.getComponent(i))
            }
        }
        for (style in styles) {
            if (
                style.getTargetClass().isAssignableFrom(root.javaClass)
                && style.getSelector().matches(root)
            ) {
                @Suppress("UNCHECKED_CAST")
                (style as Style<Component>).apply(root)

            }
        }
    }
}