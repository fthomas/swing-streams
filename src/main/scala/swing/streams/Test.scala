package swing.streams

import java.awt.event._
import javax.swing._
import scalaz.concurrent.Task
import scalaz.stream._
import scalaz.\/
import scalaz.\/._

object Test extends App {
  def mouseMotions(frame: JFrame): Process[Task, MouseEvent] = {
    class MyListener(callback: Throwable \/ MouseEvent => Unit) extends MouseMotionListener {
      def mouseDragged(event: MouseEvent): Unit = callback(right(event))
      def mouseMoved(event: MouseEvent): Unit = callback(right(event))
    }

    def readEvents(callback: Throwable \/ MouseEvent => Unit): Unit =
      frame.addMouseMotionListener(new MyListener(callback))
    // TODO: remove the listener when the Process halts

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
    .through(io.stdOutLines)
    .run.run
}
