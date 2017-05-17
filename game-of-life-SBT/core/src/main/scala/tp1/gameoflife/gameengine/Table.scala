package tp1.gameoflife.gameengine

import com.badlogic.gdx.graphics.Color

class Table (height: Int, width: Int) {

  var elements: Array[Array[Cell]] = Array.ofDim[Cell](height, width)

  val darkGrey: Color = new Color(0.2f, 0.2f, 0.2f, 1)

  def clean(): Unit = {

    for (column <- this.elements) {
      for (cell <- column) {
        cell.alive = false
        cell.afterLife = false
        cell.afterLifeCount = 0
      }
    }

  }

  def create(): Unit = {

    for (h <- 0 until height) {
      for (w <- 0 until width) {
        this.elements(h)(w) = new Cell(false, darkGrey)
      }
    }

  }

  def getHeight: Int = this.height
  def getWidth: Int = this.width

}
