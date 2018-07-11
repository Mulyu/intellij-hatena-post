package configurable

import java.awt.{GridBagConstraints, GridBagLayout, Insets}

import com.intellij.openapi.options.Configurable
import com.intellij.ui.DocumentAdapter
import javax.swing._
import javax.swing.event.DocumentEvent

class PostHatenaConfigurable extends Configurable {

  private var isModifiedByUser: Boolean = false
  private val emailField: JTextField =  new JTextField()
  private val passwordField: JPasswordField = new JPasswordField()

  override def getDisplayName: String = "Post Hatena"

  override def apply(): Unit = {

  }

  override def isModified: Boolean = isModifiedByUser

  override def createComponent(): JComponent = {

    val listener: DocumentAdapter = new DocumentAdapter {
      override def textChanged(documentEvent: DocumentEvent): Unit =
        isModifiedByUser = true
    }

    emailField.getDocument.addDocumentListener(listener)
    passwordField.getDocument.addDocumentListener(listener)

    val settingsPanel = new JPanel(new GridBagLayout())

    val base = new GridBagConstraints {
      insets = new Insets(2, 0, 2, 3)

      def setConstraints(anchor: Int = GridBagConstraints.CENTER, gridx: Int, gridy: Int, weightx: Double = 0, weighty: Double = 0, fill: Int = GridBagConstraints.NONE) = {
        this.anchor = anchor
        this.gridx = gridx
        this.gridy = gridy
        this.weightx = weightx
        this.weighty = weighty
        this.fill = fill
        this
      }
    }

    def addLabeledControl(row: Int, label: String, component: JComponent) {
      settingsPanel.add(new JLabel(label), base.setConstraints(
        anchor = GridBagConstraints.LINE_START,
        gridx = 0,
        gridy = row
      ))

      settingsPanel.add(component, base.setConstraints(
        gridx = 1,
        gridy = row,
        fill = GridBagConstraints.HORIZONTAL,
        weightx = 1.0
      ))

      settingsPanel.add(Box.createHorizontalStrut(1), base.setConstraints(
        gridx = 2,
        gridy = row,
        weightx = 0.1
      ))
    }

    addLabeledControl(1, "email", emailField)
    addLabeledControl(2, "password", passwordField)

    settingsPanel.add(new JPanel(), base.setConstraints(
      gridx = 0,
      gridy = 3,
      weighty = 10.0
    ))

    settingsPanel
  }
}