package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.{GameEngine, Table}

class WireWorld (override val height: Int, override val width: Int) extends GameEngine {

  override def toString: String = "WireWorld"

  override val description: String = "Simulates electric wires. " +
    "Alive cells can be Conduits (Yellow), Electron heads (Blue) or Electron tails (Red). " +
    "Conduits (Yellow) became Electron heads (Blue) if 1 or 2 neighbors are Electron heads; " +
    "Otherwise, they remain the same. " +
    "Electron heads (Blue) always become Electron tails (Red). " +
    "Electron tails (Red) always become Conduits (Yellow). " +
    "Dead cells always remain dead."

  val blue: Color = new Color(0, 0, 1, 1)
  val red: Color = new Color(1, 0, 0, 1)
  val yellow: Color = new Color(1, 1, 0, 1)

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
    var h = 0
    while(h < board.getHeight){
      var w = 0
      while(w < board.getWidth){

        board.elements(h)(w).color = circuitUpdate(h, w)
        w+=1
      }

      h+=1
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

    var i = -1
    while(i <= 1){
      var j = -1
      while(j <= 1){
        if (this.currentGeneration.elements(adjustHeight(cellHeight + i))(adjustWidth(cellWidth + j)).color == blue)
          chargeCount += 1
        j+=1
      }
      i+=1
    }

    chargeCount

  }

}
