package ca.weblite.ktswing.extensions

import javax.swing.JComponent

val JComponent.classList: MutableList<String>
    get() = object : AbstractMutableList<String>() {
        private val delegate: MutableList<String>
            get() = getClientProperty("cssClasses")
                ?.toString()
                ?.split(" ")
                ?.filter { it.isNotEmpty() }
                ?.toMutableList() ?: mutableListOf()

        override val size: Int get() = delegate.size
        override fun get(index: Int) = delegate[index]
        override fun set(index: Int, element: String): String {
            val tmp = delegate
            val old = tmp.set(index, element)
            flush(tmp)
            return old
        }
        override fun add(index: Int, element: String) {
            val tmp = delegate
            tmp.add(index, element)
            flush(tmp)
        }
        override fun removeAt(index: Int): String {
            val tmp = delegate
            val old = tmp.removeAt(index)
            flush(tmp)
            return old
        }
        private fun flush(del: List<String> = delegate) {
            putClientProperty("cssClasses", del.joinToString(" "))
        }
    }