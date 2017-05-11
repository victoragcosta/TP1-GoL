package tp1.gameoflife.defaultmodes

import java.util.Calendar

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.{GameEngine, Table}

import scala.util.Random

class Disco (override val height: Int, override val width: Int) extends GameEngine {

  override def toString: String = "Disco"

  override val description: String = "Somebody joked I should implement this mode, so I did. " +
    "For those that lived through the 70's. " +
    "A cell must be near 2 or 3 cells to stay alive. " +
    "A dead cell revives if there are exactly 3 cells alive near it. " +
    "All cells that are alive get random colors each generation."

  override val defaultColor = new Color(0.5f, 0.5f, 0.5f, 1)

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

  override def determineCellColor(cellHeight: Int, cellWidth: Int): Color = defaultColor

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
  
  Random.setSeed(System.nanoTime())
  
  Random.setSeed(Calendar.getInstance.getTimeInMillis)
  private def randomColor(): Color = {

    new Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), 0.9f)

  }

}
