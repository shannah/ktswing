package ca.weblite.swinky.jgoodies

import ca.weblite.swinky.AutoAddDisabled
import ca.weblite.swinky.extensions.isAutoAddEnabled
import com.jgoodies.forms.factories.DefaultComponentFactory
import com.jgoodies.forms.layout.CellConstraints
import com.jgoodies.forms.layout.FormLayout
import com.jgoodies.forms.layout.RowSpec
import java.awt.BorderLayout
import java.awt.Component
import javax.swing.JComponent
import javax.swing.JPanel

/**
 * A custom JPanel that uses BorderLayout and provides properties for each region:
 *   north, south, east, west, center.
 *
 * Setting one of these properties calls add(component, BorderLayout.XXX) internally,
 * replacing any existing component in that region.
 */
class Form(cols: String, rows: String = "") : JPanel(FormLayout(cols, rows)), AutoAddDisabled {
    private val cc = CellConstraints()
    private var currentRow = 0
    fun row(init: Form.() -> Unit) {
        val rowCount = (layout as FormLayout).rowCount
        if (rowCount > 0) {
            (layout as FormLayout).appendRow(RowSpec.decode("3dlu"))
        }
        (layout as FormLayout).appendRow(RowSpec.decode("p"))
        currentRow = (layout as FormLayout).rowCount
        init()

    }

    fun separator(title: String, init: JComponent.() -> Unit): JComponent {
        val separator = DefaultComponentFactory.getInstance().createSeparator(title)
        separator.init()
        return separator
    }

    infix fun JComponent.at(pos: CellConstraints): JComponent {
        this@Form.add(this, pos)
        return this
    }

    fun x(col: Int): CellConstraints {
        return cc.xy(col, currentRow)
    }

    fun xw(col: Int, colSpan: Int): CellConstraints {
        return cc.xyw(col, currentRow, colSpan)
    }

    fun xwh(col: Int, colSpan: Int, rowSpan: Int): CellConstraints {
        return cc.xywh(col, currentRow, colSpan, rowSpan)
    }

    fun xy(col: Int, row: Int): CellConstraints {
        return cc.xy(col, row)
    }

    fun xyw(col: Int, row: Int, colSpan: Int): CellConstraints {
        return cc.xyw(col, row, colSpan)
    }

    fun xywh(col: Int, row: Int, colSpan: Int, rowSpan: Int): CellConstraints {
        return cc.xywh(col, row, colSpan, rowSpan)
    }

    fun rc(row: Int, col: Int): CellConstraints {
        return cc.rc(row, col)
    }

    fun rcw(row: Int, col: Int, colSpan: Int): CellConstraints {
        return cc.rcw(row, col, colSpan)
    }

    fun rchw(row: Int, col: Int, rowSpan: Int, colSpan: Int): CellConstraints {
        return cc.rchw(row, col, rowSpan, colSpan)
    }


}
