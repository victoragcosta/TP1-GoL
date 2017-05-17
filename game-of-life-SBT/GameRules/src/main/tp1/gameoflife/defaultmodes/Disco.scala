package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.{GameEngine, Table}
import java.util.Calendar

import scala.util.Random

class Disco (override val height: Int, override val width: Int) extends GameEngine {

  override def toString: String = "Disco"

  override val description: String = "Somebody joked I should implement this mode, so I did. " +
    "Feel that 70's groove. " +
    "A cell must be near 2 or 3 cells to stay alive. " +
    "A dead cell revives if there are exactly 3 cells alive near it. " +
    "All cells that are alive get random colors each generation."

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

  override def switchColor (cellHeight: Int, cellWidth: Int): Unit = {

    if (this.currentGeneration.elements(cellHeight)(cellWidth).alive)
      this.currentGeneration.elements(cellHeight)(cellWidth).color = randomColor()

  }

  override def updateColors(board: Table): Unit = {

    for (h <- 0 until board.getHeight) {
      for (w <- 0 until board.getWidth) {

        board.elements(h)(w).color = randomColor()

      }
    }

  }

  Random.setSeed(Calendar.getInstance.getTimeInMillis)

  private def randomColor(): Color = new Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), 0.9f)

}
