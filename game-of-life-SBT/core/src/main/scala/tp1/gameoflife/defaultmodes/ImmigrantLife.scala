package tp1.gameoflife.defaultmodes

import java.util.Calendar

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

import scala.util.Random

class ImmigrantLife (override val height: Int, override val width: Int) extends GameEngine {

  override def toString: String = "ImmigrantLife"

  override val description: String = "The original rules, but with a twist: 2 colors." +
    "A cell must be near 2 or 3 cells to stay alive." +
    "A dead cell revives if there are exactly 3 cells alive near it." +
    "The color of the revived cell is determined by the color of the majority of cells around it." +
    "If there is a tie, the color is determined randomly."

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

  override def determineCellColor(cellHeight: Int, cellWidth: Int): Color = {

    var redCount = 0
    var blueCount = 0

    Random.setSeed(Calendar.getInstance.getTimeInMillis)

    val tieBreaker = Random.nextBoolean()

    for (i <- -1 to 1) {
      for (j <- -1 to 1) {

        if(this.currentGeneration.elements(adjustHeight(cellHeight + i))(adjustWidth(cellWidth + j)).color ==
          new Color(1, 0, 0, 0.9f))
          redCount += 1

        if(this.currentGeneration.elements(adjustHeight(cellHeight + i))(adjustWidth(cellWidth + j)).color ==
          new Color(0, 0, 1, 0.9f))
          blueCount += 1

      }
    }

    if (redCount > blueCount)
      new Color(1, 0, 0, 0.9f)

    else if (redCount < blueCount)
      new Color(0, 0, 1, 0.9f)

    else {

      if (tieBreaker)
        new Color(1, 0, 0, 0.9f)

      else
        new Color(0, 0, 1, 0.9f)

    }

  }

  def changeCellColor (cellHeight: Int, cellWidth: Int): Unit = {

    if (this.currentGeneration.elements(cellHeight)(cellWidth).alive) {

     if (this.currentGeneration.elements(cellHeight)(cellWidth).color == new Color(1, 0, 0, 0.9f))
       this.currentGeneration.elements(cellHeight)(cellWidth).color = new Color(0, 0, 1, 0.9f)

     else
       this.currentGeneration.elements(cellHeight)(cellWidth).color = new Color(1, 0, 0, 0.9f)

    }

  }

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
