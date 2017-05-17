package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

class StarWars (override val height: Int, override val width: Int) extends GameEngine {

  override def toString: String = "StarWars"

  override val description: String = "I have good feeling about this! " +
    "A cell must be near 3, 4, 5 or 6 cells to stay alive. " +
    "A dead cell revives if there are exactly 2, 7 or 8 cells alive near it. " +
    "If a cell dies, it goes into a after life state where it cannot be reborn. " +
    "A cell in after life stays in that state for 6 generations."

  val lightBlue: Color = new Color(0.25f, 0.4f, 0.9f, 0.9f)
  val mediumBlue: Color = new Color(0, 0, 0.8f, 0.9f)

  override val defaultColor: Color = mediumBlue
  override val defaultAfterLifeColor: Color = lightBlue
  override val defaultAfterLifeCount: Int = 6

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount >= 3 && aliveCount <= 6)
      true

    else
      false

  }

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (this.currentGeneration.elements(cellHeight)(cellWidth).afterLife)
      false

    else {

      if (aliveCount == 2 || aliveCount == 7 || aliveCount == 8)
        true

      else
        false

    }

  }

}