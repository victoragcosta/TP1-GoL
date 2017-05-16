package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

class HighLife (override val height: Int, override val width: Int) extends GameEngine {

  override def toString: String = "HighLife"

  override val description: String = "Good for creating replicating patterns. " +
    "A cell must be near 2 or 3 cells to stay alive. " +
    "A dead cell revives if there are 3 or 6 cells alive near it."

  val navyBlue: Color = new Color (0, 0, 0.5f, 0.9f)

  override val defaultColor: Color = navyBlue

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 2 || aliveCount == 3)
      true

    else
      false

  }

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 3 || aliveCount == 6)
      true

    else
      false

  }

}
