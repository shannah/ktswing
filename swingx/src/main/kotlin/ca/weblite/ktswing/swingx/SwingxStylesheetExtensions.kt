package ca.weblite.ktswing.swingx

import ca.weblite.ktswing.style.Style
import ca.weblite.ktswing.style.Stylesheet
import org.jdesktop.swingx.JXImagePanel
import org.jdesktop.swingx.JXSearchField


fun Stylesheet.searchField(selector: String, apply: JXSearchField.() -> Unit): Style<JXSearchField> =
    register(selector, JXSearchField::class.java, apply)

fun Stylesheet.imagePanel(selector: String, apply: JXImagePanel.() -> Unit): Style<JXImagePanel> =
    register(selector, JXImagePanel::class.java, apply)