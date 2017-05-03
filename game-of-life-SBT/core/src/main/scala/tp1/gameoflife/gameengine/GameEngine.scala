package tp1.gameoflife.gameengine

abstract class GameEngine {

  val height: Int
  val width: Int

  val mementoNumber: Int

  var currentGeneration = new Table(height, width)
  this.currentGeneration.create()

  var pastGenerations: List[Table] = List()

  def shouldRevive(cell: Cell, cellHeight: Int, cellWidth: Int): Boolean
  def shouldKeepAlive(cell: Cell, cellHeight: Int, cellWidth: Int): Boolean

  def nextGeneration(): Unit = {

    val newGeneration = new Table(height, width)
    newGeneration.create()

    for (h <- 0 until height) {
      for (w <- 0 until width) {

        if (!this.currentGeneration.elements(h)(w).alive)
          newGeneration.elements(h)(w).alive = shouldRevive(this.currentGeneration.elements(h)(w), h, w)

        if (this.currentGeneration.elements(h)(w).alive)
          newGeneration.elements(h)(w).alive = shouldKeepAlive(this.currentGeneration.elements(h)(w), h, w)

      }
    }

    storeGeneration(currentGeneration)

    this.currentGeneration = newGeneration

  }

  def printBoard (): Unit = {

    for (column <- this.currentGeneration.elements) {
      println()
      for (cell <- column) {

        if (cell.alive)
          print(1)

        else
          print(0)

      }
    }

  }

  def storeGeneration (generation: Table): Unit = {

    val length: Int = pastGenerations.length

    if (length >= mementoNumber) {

      pastGenerations = pastGenerations.dropRight(length + 1 - mementoNumber)

    }

    pastGenerations = generation :: pastGenerations

  }

  def undo (): Unit = {

    if (pastGenerations.isEmpty) {

      throw new RuntimeException

    }

    else {

      this.currentGeneration = pastGenerations.head
      pastGenerations = pastGenerations.drop(1)

    }

  }

}
