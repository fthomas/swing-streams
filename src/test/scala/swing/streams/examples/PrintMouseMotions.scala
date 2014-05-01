package swing.streams
package examples

import java.awt.event._
import javax.swing._
import scalaz.concurrent.Task
import scalaz.stream._
import scalaz.\/

import listener._

object PrintMouseMotions extends App {
  def mouseMotions(frame: JFrame): Process[Task, MouseEvent] = {
    def readEvents(callback: Throwable \/ MouseEvent => Unit): Unit =
      frame.addMouseMotionListener(new CallbackMouseMotionListener(callback))

    Process.eval(Task.async(readEvents))
  }

  val frameTask = Task.delay {
    val frame = new JFrame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.setSize(320, 240)
    frame.setVisible(true)
    frame
  }

  Process.eval(frameTask)
    .flatMap(mouseMotions)
    .map(_.toString)
    .to(io.stdOutLines)
    .run.run
}
