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

  override val defaultColor = new Color(1, 1, 0, 0.9f)

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = true

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = false

  override def switchColor (cellHeight: Int, cellWidth: Int): Unit = {

    if (this.currentGeneration.elements(cellHeight)(cellWidth).alive) {

      if (this.currentGeneration.elements(cellHeight)(cellWidth).color == defaultColor)
        this.currentGeneration.elements(cellHeight)(cellWidth).color = new Color(0, 0, 1, 0.9f)

      else if (this.currentGeneration.elements(cellHeight)(cellWidth).color == new Color(0, 0, 1, 0.9f))
        this.currentGeneration.elements(cellHeight)(cellWidth).color = new Color(1, 0, 0, 0.9f)

      else
        this.currentGeneration.elements(cellHeight)(cellWidth).color = defaultColor

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

      if (this.currentGeneration.elements(cellHeight)(cellWidth).color == new Color(1, 1, 0, 0.9f)) {

        val chargeCount = surroundingCharges(cellHeight, cellWidth)

        if (chargeCount == 1 || chargeCount == 2)
          new Color(0, 0, 1, 0.9f)

        else
          new Color(1, 1, 0, 0.9f)

      }

      else if (this.currentGeneration.elements(cellHeight)(cellWidth).color == new Color(0, 0, 1, 0.9f))
        new Color(1, 0, 0, 0.9f)

      else
        defaultColor

    }

    else
      new Color(0.2f, 0.2f, 0.2f, 1)

  }

  private def surroundingCharges (cellHeight: Int, cellWidth: Int): Int = {

    var chargeCount = 0

    for (i <- -1 to 1) {
      for (j <- -1 to 1) {

        if (this.currentGeneration.elements(adjustHeight(cellHeight + i))(adjustWidth(cellWidth + j)).color ==
          new Color(0, 0, 1, 0.9f))
          chargeCount += 1

      }
    }

    chargeCount

  }

}
