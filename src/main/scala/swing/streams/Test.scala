package swing.streams

import java.awt.event._
import javax.swing._
import scalaz.concurrent.Task
import scalaz.stream._

object Test extends App {
  val frameTask = Task.delay {
    val frame = new JFrame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.setSize(320, 240)
    frame.setVisible(true)
    frame
  }

  Process.eval(frameTask)
    .map(_.addMouseMotionListener(new MyListener))
    //.flatMap(mouseMotions)
    .run.run
}

class MyListener extends MouseMotionListener {
  def mouseDragged(event: MouseEvent): Unit = {
    println(event)
  }

  def mouseMoved(event: MouseEvent): Unit = {
    println(event)
  }
}
