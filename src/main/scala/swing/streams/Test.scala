package swing.streams

import java.awt.event._
import javax.swing._
import scalaz.concurrent.Task
import scalaz.stream._
import scalaz.\/

import listener._

object Test extends App {
  // similar functions could be added for all sorts of listeners that can be
  // added to a JFrame
  def mouseMotions(frame: JFrame): Process[Task, KeyEvent] = {
    def readEvents(callback: Throwable \/ KeyEvent => Unit): Unit =
      frame.addKeyListener(new CallbackKeyListener(callback))
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
