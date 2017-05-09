package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

class WalledCities (override val height: Int, override val width: Int) extends GameEngine {

  override def toString: String = "Walled Cities"

  override val description: String = "For fans of Medieval times. " +
    "The generated pattern is like a growing city, always surrounded by a wall. " +
    "A cell must be near 4, 5, 6, 7 or 8 cells to stay alive. " +
    "A dead cell revives if there are exactly 2, 3, 4 or 5 cells alive near it."

  override val defaultColor = new Color(0.5f, 0.5f, 0.5f, 1)

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount >= 4 && aliveCount <= 8)
      true

    else
      false

  }

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount >= 2 && aliveCount <= 5)
      true

    else
      false

  }

}
