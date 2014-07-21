package swing.streams
package examples

import java.awt.event._
import javax.swing._
import scalaz.concurrent.Task
import scalaz.stream._
import scalaz.\/

import listener._

object Dragging extends App {

  val frameTask: Task[JFrame] =
    Task.delay {
      val frame = new JFrame
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
      frame.setSize(320, 240)
      frame.setVisible(true)
      frame
    }

  def mouseEvents(frame: JFrame): Process[Task, MouseEvent] = {
    def readEvents(callback: Throwable \/ MouseEvent => Unit): Unit = {
      println("adding mouse listener")
      frame.addMouseListener(new CallbackMouseListener(callback))
    }

    Process.eval(Task.async(readEvents))
  }

  def mouseMotions(frame: JFrame): Process[Task, MouseEvent] = {
    def readEvents(callback: Throwable \/ MouseEvent => Unit): Unit = {
      println("adding mouse motion listener")
      frame.addMouseMotionListener(new CallbackMouseMotionListener(callback))
    }

    Process.eval(Task.async(readEvents))
  }

  val p = Process.eval(frameTask).flatMap { frame =>
    Process(mouseEvents(frame), mouseMotions(frame))
  }

  merge.mergeN(p)
    .map(_.toString)
    .through(io.stdOutLines)
    .run.run

}
