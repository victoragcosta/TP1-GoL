package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

class Maze (override val height: Int, override val width: Int) extends GameEngine {

  override def toString: String = "Maze"

  override val description: String = "More durable cells, perfect for mazes." +
    "A cell must be near 1, 2, 3, 4 or 5 cells to stay alive." +
    "A dead cell revives if there are exactly 3 cells alive near it."

  override val mementoNumber = 10

  override val defaultColor = new Color(0.5f, 0.5f, 0.5f, 1)

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {

    var aliveCount: Int = -1

    for (i <- -1 to 1) {
      for (j <- -1 to 1) {

        if (this.currentGeneration.elements(adjustHeight(cellHeight + i))(adjustWidth(cellWidth + j)).alive)
          aliveCount += 1

      }
    }

    if (aliveCount >= 1 && aliveCount <= 5)
      true

    else
      false

  }

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {

    var aliveCount: Int = 0

    for (i <- -1 to 1) {
      for (j <- -1 to 1) {

        if(this.currentGeneration.elements(adjustHeight(cellHeight + i))(adjustWidth(cellWidth + j)).alive)
          aliveCount += 1

      }
    }

    if (aliveCount == 3)
      true

    else
      false

  }

  override def determineCellColor(cellHeight: Int, cellWidth: Int): Color = defaultColor

  private def adjustHeight (value: Int): Int = {

    if (value < 0)
      value + height

    else if (value >= height)
      value - height

    else
      value

  }

  private def adjustWidth (value: Int): Int = {

    if (value < 0)
      value + width

    else if (value >= width)
      value - width

    else
      value

  }

}
