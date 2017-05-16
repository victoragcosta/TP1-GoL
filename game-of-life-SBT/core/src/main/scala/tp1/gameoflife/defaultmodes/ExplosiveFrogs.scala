package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

class ExplosiveFrogs (override val height: Int, override val width: Int) extends GameEngine {

  override def toString: String = "ExplosiveFrogs"

  override val description: String = "Ribbit, ribbit, kaboom! " +
    "A cell must be near 1, 2 or 4 cells to stay alive. " +
    "A dead cell revives if there are exactly 3 cells alive near it. " +
    "If a cell dies, it goes into a after life state where it cannot be reborn. " +
    "A cell in after life stays in that state for 3 generations."

  val lawnGreen: Color = new Color(0.5f, 1, 0, 0.9f)
  val green: Color = new Color(0, 0.5f, 0, 0.9f)

  override val defaultColor: Color = green
  override val defaultAfterLifeColor: Color = lawnGreen
  override val defaultAfterLifeCount: Int = 3

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 1 || aliveCount == 2 || aliveCount == 4)
      true

    else
      false

  }

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (this.currentGeneration.elements(cellHeight)(cellWidth).afterLife)
      false

    else {

      if (aliveCount == 3)
        true

      else
        false

    }

  }

}
