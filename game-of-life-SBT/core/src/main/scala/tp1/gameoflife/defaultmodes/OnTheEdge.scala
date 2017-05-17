package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

class OnTheEdge (override val height: Int, override val width: Int) extends GameEngine {

  override def toString: String = "OnTheEdge"

  override val description: String = "Living on the edge. " +
    "A cell must be near 3, 4, 5 or 8 cells to stay alive. " +
    "A dead cell revives if there are exactly 3 or 7 cells alive near it. " +
    "If a cell dies, it goes into a after life state where it cannot be reborn. " +
    "A cell in after life stays in that state for 4 generations."

  val red: Color = new Color(1, 0, 0, 0.9f)
  val yellow: Color = new Color(1, 1, 0, 0.9f)

  override val defaultColor: Color = red
  override val defaultAfterLifeColor: Color = yellow
  override val defaultAfterLifeCount: Int = 4

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if ((aliveCount >= 3 && aliveCount <= 5) || aliveCount == 8)
      true

    else
      false

  }

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (this.currentGeneration.elements(cellHeight)(cellWidth).afterLife)
      false

    else {

      if (aliveCount == 3 || aliveCount == 7)
        true

      else
        false

    }

  }

}