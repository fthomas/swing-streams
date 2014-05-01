package swing.streams

import java.awt.event._
import scalaz.\/
import scalaz.\/._

object listener {
  type Callback[A] = Throwable \/ A => Unit

  class CallbackKeyListener(cb: Callback[KeyEvent]) extends KeyListener {
    def keyPressed(event: KeyEvent): Unit = cb(right(event))
    def keyReleased(event: KeyEvent): Unit = cb(right(event))
    def keyTyped(event: KeyEvent): Unit = cb(right(event))
  }

  class CallbackMouseListener(cb: Callback[MouseEvent]) extends MouseListener {
    def mouseClicked(event: MouseEvent): Unit = cb(right(event))
    def mouseEntered(event: MouseEvent): Unit = cb(right(event))
    def mouseExited(event: MouseEvent): Unit = cb(right(event))
    def mousePressed(event: MouseEvent): Unit = cb(right(event))
    def mouseReleased(event: MouseEvent): Unit = cb(right(event))
  }

  class CallbackMouseMotionListener(cb: Callback[MouseEvent]) extends MouseMotionListener {
    def mouseDragged(event: MouseEvent): Unit = cb(right(event))
    def mouseMoved(event: MouseEvent): Unit = cb(right(event))
  }

  class CallbackMouseWheelListener(cb: Callback[MouseWheelEvent]) extends MouseWheelListener {
    def mouseWheelMoved(event: MouseWheelEvent): Unit = cb(right(event))
  }
}
