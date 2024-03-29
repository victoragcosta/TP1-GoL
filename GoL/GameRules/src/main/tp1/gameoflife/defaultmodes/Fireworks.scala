package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

class Fireworks (override val height: Int, override val width: Int) extends GameEngine {

  override def toString: String = "Fireworks"

  override val description: String = "Each cell explodes into a beautiful pattern. " +
    "A cell must be near 1 cell to stay alive. " +
    "A dead cell revives if there is exactly 1 cell alive near it."

  val darkOrange: Color = new Color(1, 0.55f, 0, 0.9f)

  override val defaultColor: Color = darkOrange

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 1)
      true

    else
      false

  }

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 1)
      true

    else
      false

  }

}
