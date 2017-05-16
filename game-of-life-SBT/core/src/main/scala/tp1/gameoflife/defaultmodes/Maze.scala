package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

class Maze (override val height: Int, override val width: Int) extends GameEngine {

  override def toString: String = "Maze"

  override val description: String = "Can anyone see the exit? " +
    "A cell must be near 1, 2, 3, 4 or 5 cells to stay alive. " +
    "A dead cell revives if there are exactly 3 cells alive near it. "

  val gold: Color = new Color(1, 0.8f, 0, 0.9f)

  override val defaultColor: Color = gold

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount >= 1 && aliveCount <= 5)
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
