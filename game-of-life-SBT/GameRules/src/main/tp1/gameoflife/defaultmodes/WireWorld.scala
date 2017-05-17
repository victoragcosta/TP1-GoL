package tp1.gameoflife.defaultmodes

import java.util.Calendar

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.{GameEngine, Table}

import scala.util.Random

class WireWorld (override val height: Int, override val width: Int) extends GameEngine {

  override def toString: String = "WireWorld"

  override val description: String = "Simulates electric wires. " +
    "Alive cells can be Conduits (Yellow), Electron heads (Blue) or Electron tails (Red). " +
    "Conduits (Yellow) became Electron heads (Blue) if 1 or 2 neighbors are Electron heads; " +
    "Otherwise, they remain the same. " +
    "Electron heads (Blue) always become Electron tails (Red). " +
    "Electron tails (Red) always become Conduits (Yellow). " +
    "Dead cells always remain dead."

  val blue: Color = new Color(0, 0, 1, 0.9f)
  val red: Color = new Color(1, 0, 0, 0.9f)
  val yellow: Color = new Color(1, 1, 0, 0.9f)

  override val defaultColor: Color = yellow

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = true

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = false

  override def switchColor (cellHeight: Int, cellWidth: Int): Unit = {

    if (this.currentGeneration.elements(cellHeight)(cellWidth).alive) {

      if (this.currentGeneration.elements(cellHeight)(cellWidth).color == yellow)
        this.currentGeneration.elements(cellHeight)(cellWidth).color = blue

      else if (this.currentGeneration.elements(cellHeight)(cellWidth).color == blue)
        this.currentGeneration.elements(cellHeight)(cellWidth).color = red

      else
        this.currentGeneration.elements(cellHeight)(cellWidth).color = yellow

    }

  }

  override def updateColors(board: Table): Unit = {

    for (h <- 0 until board.getHeight) {
      for (w <- 0 until board.getWidth) {

        board.elements(h)(w).color = circuitUpdate(h, w)

      }
    }

  }

  private def circuitUpdate (cellHeight: Int, cellWidth: Int): Color = {

    if (this.currentGeneration.elements(cellHeight)(cellWidth).alive) {

      if (this.currentGeneration.elements(cellHeight)(cellWidth).color == yellow) {

        val chargeCount = surroundingCharges(cellHeight, cellWidth)

        if (chargeCount == 1 || chargeCount == 2)
          blue

        else
          yellow

      }

      else if (this.currentGeneration.elements(cellHeight)(cellWidth).color == blue)
        red

      else
        yellow

    }

    else
      defaultDeathColor

  }

  private def surroundingCharges (cellHeight: Int, cellWidth: Int): Int = {

    var chargeCount = 0

    for (i <- -1 to 1) {
      for (j <- -1 to 1) {

        if (this.currentGeneration.elements(adjustHeight(cellHeight + i))(adjustWidth(cellWidth + j)).color == blue)
          chargeCount += 1

      }
    }

    chargeCount

  }

}
