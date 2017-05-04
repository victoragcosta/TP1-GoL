package tp1.gameoflife.gameengine

class Table (height: Int, width: Int) {

  var elements:Array[Array[Cell]] = Array.ofDim[Cell](height, width)

  def clean(): Unit = {

    for (column <- this.elements) {
      for (cell <- column) {
        cell.alive = false
      }
    }

  }

  def create(): Unit = {

    for (h <- 0 until height) {
      for (w <- 0 until width) {
        this.elements(h)(w) = new Cell(false)
      }
    }

  }

}
