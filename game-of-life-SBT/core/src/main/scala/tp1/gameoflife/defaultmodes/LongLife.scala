package tp1.gameoflife.defaultmodes

/**
  * Created by hugon on 13-May-17.
  */
import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

class LongLife (override val height: Int, override val width: Int) extends GameEngine{

  override def toString: String = "LongLife"

  override val description: String = "Long Life particles actually don't resemble normal Life particles, being an alien type all their own. They tend to generate diamond shaped oscillators that essentially become stable and never die"

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 5)
      true

    else
      false
  }

  override val defaultColor = new Color(0.5f, 0.5f, 0.5f, 1)

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 3 || aliveCount == 4 || aliveCount == 5)
      true

    else
      false
  }
}
