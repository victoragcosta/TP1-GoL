package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.Statistics

object TestEnvironment extends App{

  val Board = new Classic(15, 15)

  println(Board.toString)

  print(Board.description)

  Board.reviveCell(1, 1)
  Board.reviveCell(2, 1)
  Board.reviveCell(3, 1)
  Board.reviveCell(2, 2)
  Board.reviveCell(0, 1)
  Board.reviveCell(4, 1)
  Board.reviveCell(0, 0)
  Board.reviveCell(5, 1)
  Board.reviveCell(2, 3)
  Board.reviveCell(1, 3)
  Board.reviveCell(5, 0)
  Board.reviveCell(5, 2)
  Board.reviveCell(5, 3)
  Board.reviveCell(6, 1)
  Board.reviveCell(6, 2)
  Board.reviveCell(6, 3)
  Board.reviveCell(7, 1)
  Board.reviveCell(7, 2)

  println()
  Board.printBoard()
  println()

  for (i <- 0 until 10) {

    Board.nextGeneration()
    println()
    Board.printBoard()
    println()
    println(Board.cellsAlive())
    println()

  }

  Statistics.displayStatistics()

}
