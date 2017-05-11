package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

class Replicator (override val height: Int, override val width: Int) extends GameEngine {

  override def toString: String = "Replicator"

  override val description: String = "Fractals, that's why! " +
    "A cell must be near 1, 3, 5 or 7 cells to stay alive. " +
    "A dead cell revives if there are exactly 1, 3, 5 or 7 cells alive near it."

  override val defaultColor = new Color(0.5f, 0.5f, 0.5f, 1)

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 1 || aliveCount == 3 || aliveCount == 5 || aliveCount == 7)
      true

    else
      false

  }

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 1 || aliveCount == 3 || aliveCount == 5 || aliveCount == 7)
      true

    else
      false

  }


}
