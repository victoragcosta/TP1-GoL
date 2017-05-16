package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

class Gliders (override val height: Int, override val width: Int) extends GameEngine {

  override def toString: String = "Gliders"

  override val description: String = "Is this a racing game from a old school emulator? " +
    "A cell must be near 6 cells to stay alive. " +
    "A dead cell revives if there are exactly 2, 4 or 6 cells alive near it. " +
    "If a cell dies, it goes into a after life state where it cannot be reborn. " +
    "A cell in after life stays in that state for 3 generations."

  val lightBlue: Color = new Color(0.25f, 0.4f, 0.9f, 0.9f)
  val yellow: Color = new Color(1, 1, 0, 0.9f)

  override val defaultColor: Color = lightBlue
  override val defaultAfterLifeColor: Color = yellow
  override val defaultAfterLifeCount: Int = 3

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 6)
      true

    else
      false

  }

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (this.currentGeneration.elements(cellHeight)(cellWidth).afterLife)
      false

    else {

      if (aliveCount == 2 || aliveCount == 4 || aliveCount == 6)
        true

      else
        false

    }

  }

}