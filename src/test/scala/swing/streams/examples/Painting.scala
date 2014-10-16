package swing.streams
package examples

import java.awt.event.MouseEvent
import java.awt.image.BufferedImage
import java.awt.{ Dimension, Graphics }
import javax.swing.{ JFrame, JPanel }

import scalaz.\/
import scalaz.concurrent.Task
import scalaz.stream._

object Painting extends App {

  def mouseMotions(frame: JFrame): Process[Task, MouseEvent] = {
    def readEvents(callback: Throwable \/ MouseEvent => Unit): Unit =
      frame.addMouseMotionListener(new listener.CallbackMouseMotionListener(callback))
    Process.eval(Task.async(readEvents))
  }

  def canvasSink(canvas: Canvas): Sink[Task, MouseEvent] =
    Process.constant(event => Task.now(canvas.paint(event.getX, event.getY)))

  val frameTask = Task.delay {
    val frame = new JFrame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.setVisible(true)

    val canvas = new Canvas
    frame.add(canvas)
    frame.pack()

    (frame, canvas)
  }

  val p = Process.eval(frameTask).flatMap {
    case (frame, canvas) =>
      mouseMotions(frame) to canvasSink(canvas)
  }

  p.run.run
}

class Canvas extends JPanel {
  private val imgWidth = 320
  private val imgHeight = 240

  private val img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB)

  override val getPreferredSize: Dimension =
    new Dimension(imgWidth, imgHeight)

  override def paintComponent(g: Graphics): Unit = {
    super.paintComponent(g)
    g.drawImage(img, 0, 0, imgWidth, imgHeight, null)
    ()
  }

  def paint(x: Int, y: Int): Unit = {
    img.setRGB(x, y, 0xFFFFFF)
    repaint(x, y, 1, 1)
  }
}
