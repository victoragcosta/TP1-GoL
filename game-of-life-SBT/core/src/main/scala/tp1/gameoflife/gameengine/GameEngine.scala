package tp1.gameoflife.gameengine

abstract class GameEngine {

  val description: String

  val height: Int
  val width: Int

  val mementoNumber: Int

  var currentGeneration = new Table(height, width)
  this.currentGeneration.create()

  private var pastGenerations: List[Table] = List()

  def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean
  def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean

  def nextGeneration(): Unit = {

    val newGeneration = new Table(height, width)
    newGeneration.create()

    for (h <- 0 until height) {
      for (w <- 0 until width) {

        if (!this.currentGeneration.elements(h)(w).alive)
          newGeneration.elements(h)(w).alive = shouldRevive(h, w)

        if (this.currentGeneration.elements(h)(w).alive)
          newGeneration.elements(h)(w).alive = shouldKeepAlive(h, w)

      }
    }

    storeGeneration(this.currentGeneration)

    this.currentGeneration = newGeneration

  }

  def printBoard(): Unit = {

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

  private def storeGeneration(generation: Table): Unit = {

    val length: Int = pastGenerations.length

    if (length >= mementoNumber) {

      pastGenerations = pastGenerations.dropRight(length + 1 - mementoNumber)

    }

    pastGenerations = generation :: pastGenerations

  }

  def undo(): Unit = {

    if (pastGenerations.isEmpty) {

      throw new RuntimeException

    }

    else {

      this.currentGeneration = pastGenerations.head
      pastGenerations = pastGenerations.drop(1)

    }

  }

  def makeAlive(x: Int, y: Int): Unit ={
    this.currentGeneration.elements(y)(x) = new Cell(true)
  }
  def killCell(x: Int, y: Int): Unit ={
    this.currentGeneration.elements(y)(x) = new Cell(false)
  }

}
