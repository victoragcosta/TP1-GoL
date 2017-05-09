package tp1.gameoflife.gameengine

import com.badlogic.gdx.graphics.Color

abstract class GameEngine {

  val description: String

  val height: Int
  val width: Int

  val mementoNumber: Int

  var currentGeneration = new Table(height, width)
  this.currentGeneration.create()

  private var pastGenerations: List[Table] = List()

  val defaultColor: Color

  def toString: String

  def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean
  def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean
  def determineCellColor(cellHeight: Int, cellWidth: Int): Color

  def reviveCell(cellHeight: Int, cellWidth: Int): Unit = {

    this.currentGeneration.elements(cellHeight)(cellWidth).alive = true
    this.currentGeneration.elements(cellHeight)(cellWidth).color = defaultColor

  }

  def killCell(cellHeight: Int, cellWidth: Int): Unit = {

    this.currentGeneration.elements(cellHeight)(cellWidth).alive = false
    this.currentGeneration.elements(cellHeight)(cellWidth).color = new Color(0.2f, 0.2f, 0.2f, 1)

  }

  def nextGeneration(): Unit = {

    val newGeneration = new Table(height, width)
    newGeneration.create()

    for (h <- 0 until height) {
      for (w <- 0 until width) {

        if (!this.currentGeneration.elements(h)(w).alive) {

          newGeneration.elements(h)(w).alive = shouldRevive(h, w)

          if (shouldRevive(h, w))
            newGeneration.elements(h)(w).color = determineCellColor(h, w)

          else
            newGeneration.elements(h)(w).color = new Color(0.2f, 0.2f, 0.2f, 1)

        }

        if (this.currentGeneration.elements(h)(w).alive) {

          newGeneration.elements(h)(w).alive = shouldKeepAlive(h, w)

          if (shouldKeepAlive(h, w))
            newGeneration.elements(h)(w).color = this.currentGeneration.elements(h)(w).color

          else
            newGeneration.elements(h)(w).color = new Color(0.2f, 0.2f, 0.2f, 1)

        }

      }
    }

    storeGeneration(this.currentGeneration)

    this.currentGeneration = newGeneration

  }

  def printBoard(): Unit = {

    for (column <- this.currentGeneration.elements) {
      println()
      for (cell <- column) {

        if (cell.alive) {

          if(cell.color == new Color(0.5f, 0.5f, 0.5f, 1))
            print(1)

          else if(cell.color == new Color(1, 0, 0, 0.9f))
            print(2)

          else if(cell.color == new Color(0, 1, 0, 0.9f))
            print(3)

          else if(cell.color == new Color(0, 0, 1, 0.9f))
            print(4)

          else
            print(5)

        }

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

  def switchColor (cellHeight: Int, cellWidth: Int): Unit = {}

  def isAlive(cellHeight: Int, cellWidth: Int): Boolean = this.currentGeneration.elements(cellHeight)(cellWidth).alive

}
