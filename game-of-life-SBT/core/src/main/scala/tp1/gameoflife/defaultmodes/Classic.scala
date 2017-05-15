package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

class Classic (override val height: Int, override val width: Int) extends GameEngine {

  override def toString: String = "Classic"

  override val description: String = "The original rules. A cell must be near 2 or 3 cells to stay alive. " +
    "A dead cell revives if there are exactly 3 cells alive near it."

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 2 || aliveCount == 3)
      true

    else
      false

  }

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 3)
      true

    else
      false

  }

}
