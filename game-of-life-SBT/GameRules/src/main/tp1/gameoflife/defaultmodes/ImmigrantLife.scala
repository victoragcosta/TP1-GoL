package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine
import java.util.Calendar
import scala.util.Random

class ImmigrantLife (override val height: Int, override val width: Int) extends GameEngine {

  override def toString: String = "ImmigrantLife"

  override val description: String = "The original rules, but with a twist: 2 colors. " +
    "A cell must be near 2 or 3 cells to stay alive. " +
    "A dead cell revives if there are exactly 3 cells alive near it. " +
    "The color of the revived cell is determined by the color of the majority of cells around it. " +
    "If there is a tie, the color is determined randomly."

  val blue: Color = new Color(0, 0, 1, 0.9f)
  val red: Color = new Color(1, 0, 0, 0.9f)

  override val defaultColor: Color = red

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 2 || aliveCount == 3)
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

  override def determineCellColor(cellHeight: Int, cellWidth: Int): Color = {

    var redCount = 0
    var blueCount = 0

    Random.setSeed(Calendar.getInstance.getTimeInMillis)

    val tieBreaker = Random.nextBoolean()

    for (i <- -1 to 1) {
      for (j <- -1 to 1) {

        if(this.currentGeneration.elements(adjustHeight(cellHeight + i))(adjustWidth(cellWidth + j)).color == red)
          redCount += 1

        if(this.currentGeneration.elements(adjustHeight(cellHeight + i))(adjustWidth(cellWidth + j)).color == blue)
          blueCount += 1

      }
    }

    if (redCount > blueCount)
      red

    else if (redCount < blueCount)
      blue

    else {

      if (tieBreaker)
        red

      else
        blue

    }

  }

  override def switchColor (cellHeight: Int, cellWidth: Int): Unit = {

    if (this.currentGeneration.elements(cellHeight)(cellWidth).alive) {

     if (this.currentGeneration.elements(cellHeight)(cellWidth).color == red)
       this.currentGeneration.elements(cellHeight)(cellWidth).color = blue

     else
       this.currentGeneration.elements(cellHeight)(cellWidth).color = red

    }

  }

}
