package ca.weblite.ktswing.style

import java.awt.Component

class ChainableStyle<T: Component>(targetType: Class<T>, selector: Selector) : AbstractStyle<T>(targetType,selector) {

    private val childStyleSheets: MutableList<Stylesheet> = mutableListOf()

    private var wrappedStyle: Style<T>? = null

    constructor(wrappedStyle: Style<T>): this(wrappedStyle.getTargetClass(), wrappedStyle.getSelector()) {
        this.wrappedStyle = wrappedStyle
    }

    override fun apply(target: T) {
        wrappedStyle?.apply(target)
        childStyleSheets.forEach { it.apply(target) }
    }

    fun <E: Component> add(style: Style<E>): ChainableStyle<E> {
        val sheet = Stylesheet()
        val chainableStyle = asChainable(style)
        sheet.register(asChainable(style))
        childStyleSheets.add(sheet)
        return chainableStyle
    }

    private fun <E: Component> asChainable(style: Style<E>): ChainableStyle<E> {
        if (style is ChainableStyle) return style as ChainableStyle<E>
        return ChainableStyle(style)
    }

}