package swing.streams

import java.awt.event._
import scalaz.\/
import scalaz.\/._

object listener {
  class CallbackKeyListener(callback: Throwable \/ KeyEvent => Unit)
      extends KeyListener {
    def keyPressed(event: KeyEvent): Unit = callback(right(event))
    def keyReleased(event: KeyEvent): Unit = callback(right(event))
    def keyTyped(event: KeyEvent): Unit = callback(right(event))
  }

  class CallbackMouseListener(callback: Throwable \/ MouseEvent => Unit)
      extends MouseListener {
    def mouseClicked(event: MouseEvent): Unit = callback(right(event))
    def mouseEntered(event: MouseEvent): Unit = callback(right(event))
    def mouseExited(event: MouseEvent): Unit = callback(right(event))
    def mousePressed(event: MouseEvent): Unit = callback(right(event))
    def mouseReleased(event: MouseEvent): Unit = callback(right(event))
  }

  class CallbackMouseMotionListener(callback: Throwable \/ MouseEvent => Unit)
      extends MouseMotionListener {
    def mouseDragged(event: MouseEvent): Unit = callback(right(event))
    def mouseMoved(event: MouseEvent): Unit = callback(right(event))
  }

  class CallbackMouseWheelListener(callback: Throwable \/ MouseWheelEvent => Unit)
      extends MouseWheelListener {
    def mouseWheelMoved(event: MouseWheelEvent): Unit = callback(right(event))
  }
}
