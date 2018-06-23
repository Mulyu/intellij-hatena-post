import java.awt.{BorderLayout, Dimension, GridLayout}

import com.intellij.openapi.actionSystem.{AnAction, AnActionEvent, CommonDataKeys, PlatformDataKeys}
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.border.Border
import javax.swing._

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

    new SampleDialog(anActionEvent.getProject).show()
    println(postText)
  }

  override def update(e: AnActionEvent): Unit = {

  }
}


class SampleDialog(
  project: Project
) extends DialogWrapper(project) {
  init()
  setTitle("title")


  private val editorTabPanel: EditorTabPanel = new EditorTabPanel()

  private val previewTabPanel: PreviewTabPanel = new PreviewTabPanel()

  override def createCenterPanel(): JComponent = EditorTabbedPane(editorTabPanel, previewTabPanel)
}

class EditorTabPanel extends JPanel {

}

class PreviewTabPanel extends JPanel {

}

case class EditorTabbedPane(
  editor: EditorTabPanel,
  preview : PreviewTabPanel
) extends JTabbedPane {
  addTab("Editor", editor)
  addTab("Preview", editor)

  setPreferredSize(new Dimension(700, 500))
}