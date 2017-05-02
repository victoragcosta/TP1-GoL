package tp1.gameoflife.gameengine

abstract class GameEngine {

  val height: Int
  val width: Int

  var table:Array[Array[Cell]] = Array.ofDim[Cell](height, width)

  for (h <- 0 until height) {
    for (w <- 0 until width) {

      table(h)(w).alive = false

    }
  }

  def shouldRevive(cell: Cell): Boolean
  def shouldKeepAlive(cell: Cell): Boolean

  def nextGeneration(): Unit = {

    val newTable:Array[Array[Cell]] = Array.ofDim[Cell](height, width)

    for (h <- 0 until height) {
      for (w <- 0 until width) {

        if (!table(h)(w).alive)
          newTable(h)(w).alive = shouldRevive(table(h)(w))

        if (table(h)(w).alive)
          newTable(h)(w).alive = shouldKeepAlive(table(h)(w))

      }
    }

    this.table = newTable

  }

}
