package ca.weblite.swinky.swingx

import ca.weblite.swinky.style.Style
import ca.weblite.swinky.style.Stylesheet
import org.jdesktop.swingx.JXImagePanel
import org.jdesktop.swingx.JXSearchField


fun Stylesheet.searchField(selector: String, apply: JXSearchField.() -> Unit): Style<JXSearchField> =
    register(selector, JXSearchField::class.java, apply)

fun Stylesheet.imagePanel(selector: String, apply: JXImagePanel.() -> Unit): Style<JXImagePanel> =
    register(selector, JXImagePanel::class.java, apply)