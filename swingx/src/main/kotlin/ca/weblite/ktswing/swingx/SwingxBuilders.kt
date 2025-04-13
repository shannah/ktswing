package ca.weblite.ktswing.swingx

import ca.weblite.ktswing.extensions.createComponent
import org.jdesktop.swingx.*
import java.awt.Container
import java.net.URL

fun Container.searchField(init: JXSearchField.() -> Unit = {}): JXSearchField  =
    createComponent(factory = { JXSearchField() }, init = init)

fun Container.imagePanel(init: JXImagePanel.() -> Unit = {}): JXImagePanel
    = createComponent(factory = null, init = init)

fun Container.jxLabel(init: JXLabel.() -> Unit = {}): JXLabel =
    createComponent(factory = { JXLabel() }, init = init)

fun Container.busyLabel(init: JXBusyLabel.() -> Unit = {}): JXBusyLabel =
    createComponent(factory = { JXBusyLabel() }, init = init)

fun Container.jxButton(init: JXButton.() -> Unit = {}): JXButton =
    createComponent(factory = { JXButton() }, init = init)

fun Container.jxTextField(init: JXTextField.() -> Unit = {}): JXTextField =
    createComponent(factory = { JXTextField() }, init = init)

fun Container.jxTextArea(init: JXTextArea.() -> Unit = {}): JXTextArea =
    createComponent(factory = { JXTextArea() }, init = init)

fun Container.jxTaskPane(init: JXTaskPane.() -> Unit = {}): JXTaskPane =
    createComponent(factory = { JXTaskPane() }, init = init)

fun Container.jxTaskPaneContainer(init: JXTaskPaneContainer.() -> Unit = {}): JXTaskPaneContainer =
    createComponent(factory = { JXTaskPaneContainer() }, init = init)

fun Container.jxCollapsiblePane(init: JXCollapsiblePane.() -> Unit = {}): JXCollapsiblePane =
    createComponent(factory = { JXCollapsiblePane() }, init = init)

fun Container.jxPanel(init: JXPanel.() -> Unit = {}): JXPanel =
    createComponent(factory = { JXPanel() }, init = init)

fun Container.jxHyperlink(init: JXHyperlink.() -> Unit = {}): JXHyperlink =
    createComponent(factory = { JXHyperlink() }, init = init)

fun Container.jxDatePicker(init: JXDatePicker.() -> Unit = {}): JXDatePicker =
    createComponent(factory = { JXDatePicker() }, init = init)

fun Container.jxMonthView(init: JXMonthView.() -> Unit = {}): JXMonthView =
    createComponent(factory = { JXMonthView() }, init = init)

fun Container.jxHeader(init: JXHeader.() -> Unit = {}): JXHeader =
    createComponent(factory = { JXHeader() }, init = init)

fun Container.jxStatusBar(init: JXStatusBar.() -> Unit = {}): JXStatusBar =
    createComponent(factory = { JXStatusBar() }, init = init)

fun Container.jxTipOfTheDay(init: JXTipOfTheDay.() -> Unit = {}): JXTipOfTheDay =
    createComponent(factory = { JXTipOfTheDay() }, init = init)

fun Container.jxList(init: JXList.() -> Unit = {}): JXList =
    createComponent(factory = { JXList() }, init = init)

fun Container.jxTreeTable(init: JXTreeTable.() -> Unit = {}): JXTreeTable =
    createComponent(factory = { JXTreeTable() }, init = init)

fun Container.jxTable(init: JXTable.() -> Unit = {}): JXTable =
    createComponent(factory = { JXTable() }, init = init)


fun Container.imagePanel(imageUrl: URL, init: JXImagePanel.() -> Unit = {}): JXImagePanel =
    createComponent(factory = { JXImagePanel(imageUrl) }, init = init)