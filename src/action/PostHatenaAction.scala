package action

import java.awt.Dimension

import com.intellij.openapi.actionSystem.{AnAction, AnActionEvent, CommonDataKeys}
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import javax.swing._
import javax.swing.event.{DocumentEvent, DocumentListener}

class PostHatenaAction extends AnAction {

  override def actionPerformed(anActionEvent: AnActionEvent): Unit = {
    val editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR)
    val document = editor.getDocument
    val selection = editor.getSelectionModel

    val postText = (Option(selection.getSelectedText), Option(document.getText)) match {
      case (t: Some[String], _) => t
      case (_, t: Some[String]) => t
      case _ => None
    }

    new HatenaPostDialog(anActionEvent.getProject).show()
    println(postText)
  }

  override def update(e: AnActionEvent): Unit = {

  }
}

class HatenaPostDialog(
  project: Project
) extends DialogWrapper(project) {
  init()
  setTitle("HatenaPost")

  override def createCenterPanel(): JComponent = {
    new EditorTabbedPane()
  }
}

case class EditorTabPanelDelegate(
  onChangeText: String => Unit
)

class EditorTabPanel(
  delegate: EditorTabPanelDelegate
) {
  val textArea = new JTextArea()

  textArea.getDocument.addDocumentListener(new DocumentListener {
    override def insertUpdate(documentEvent: DocumentEvent): Unit =
      delegate.onChangeText(textArea.getText)

    override def removeUpdate(documentEvent: DocumentEvent): Unit =
      delegate.onChangeText(textArea.getText)

    override def changedUpdate(documentEvent: DocumentEvent): Unit =
      delegate.onChangeText(textArea.getText)
  })
}

case class PreviewTabPanelDelegate(
  updateText: String => Unit
)

class PreviewTabPanel {
  val textArea = new JTextArea()
  textArea.setEditable(false)
}

class EditorTabbedPane extends JTabbedPane {
  val preview = new PreviewTabPanel()
  val editor = new EditorTabPanel(
    EditorTabPanelDelegate(
      str => preview.textArea.setText(str)
    )
  )

  addTab("Editor", editor.textArea)
  addTab("Preview", preview.textArea)

  setPreferredSize(new Dimension(700, 500))
}