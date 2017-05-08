package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

class Classic (override val height: Int, override val width: Int) extends GameEngine {

  override def toString: String = "Classic"

  override val description: String = "The original rules. A cell must be near 2 or 3 cells to stay alive." +
    "A dead cell revives if there are exactly 3 cells alive near it."

  override val mementoNumber = 10

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {

    var aliveCount: Int = -1

    for (i <- -1 to 1) {
      for (j <- -1 to 1) {

        if (this.currentGeneration.elements(adjustHeight(cellHeight + i))(adjustWidth(cellWidth + j)).alive)
          aliveCount += 1

      }
    }

    if (aliveCount == 2 || aliveCount == 3)
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

  override def determineCellColor(cellHeight: Int, cellWidth: Int): Color = new Color(0.5f, 0.5f, 0.5f, 1)

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
