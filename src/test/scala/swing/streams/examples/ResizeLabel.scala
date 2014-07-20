package swing.streams
package examples

import java.awt.Font
import java.awt.event.KeyEvent
import javax.swing._
import scalaz.concurrent.Task
import scalaz.stream._
import scalaz.\/

import listener._

object ResizeLabel extends App {

  val frameTask: Task[JFrame] =
    Task.delay {
      val frame = new JFrame
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
      frame.setSize(320, 240)
      frame.setVisible(true)
      frame
    }

  val frameLabelTask: Task[(JFrame, JLabel)] =
    frameTask.map { frame =>
      val text = "Hello World!"
      val label = new JLabel(text, SwingConstants.CENTER)
      frame.getContentPane.add(label)
      (frame, label)
    }

  def readAdjustments(frame: JFrame): Process[Task, Int] = {
    def readEvents(callback: Throwable \/ KeyEvent => Unit): Unit =
      frame.addKeyListener(new CallbackKeyListener(callback))

    Process.eval(Task.async(readEvents)).map(_.getKeyCode).collect {
      case KeyEvent.VK_UP => 1
      case KeyEvent.VK_DOWN => -1
    }
  }

  def incrFontSize(font: Font, incr: Int): Font = {
    val size = font.getSize + incr
    new Font(font.getFontName, font.getStyle, size)
  }

  Process.eval(frameLabelTask)
    .flatMap { case (frame, label) => readAdjustments(frame).map((_, label)) }
    .map { case (adj, label) => label.setFont(incrFontSize(label.getFont, adj)) }
    .run.run
}
