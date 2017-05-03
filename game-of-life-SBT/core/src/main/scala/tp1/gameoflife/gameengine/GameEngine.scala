package tp1.gameoflife.gameengine

abstract class GameEngine {

  val height: Int
  val width: Int

  val mementoNumber: Int

  var currentGeneration = new Table(height, width)
  currentGeneration.clean()

  var pastGenerations: List[Table] = List()

  def shouldRevive(cell: Cell): Boolean
  def shouldKeepAlive(cell: Cell): Boolean

  def nextGeneration(): Unit = {

    val newGeneration = new Table(height, width)

    for (h <- 0 until height) {
      for (w <- 0 until width) {

        if (!currentGeneration.elements(h)(w).alive)
          newGeneration.elements(h)(w).alive = shouldRevive(currentGeneration.elements(h)(w))

        if (currentGeneration.elements(h)(w).alive)
          newGeneration.elements(h)(w).alive = shouldKeepAlive(currentGeneration.elements(h)(w))

      }
    }

    storeGeneration(currentGeneration)

    this.currentGeneration = newGeneration

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
