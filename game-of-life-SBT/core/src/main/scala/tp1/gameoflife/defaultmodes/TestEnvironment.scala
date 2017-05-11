package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.Statistics

object TestEnvironment extends App{

  val Board = new WireWorld(7, 7)

  println(Board.toString)

  print(Board.description)

  Board.reviveCell(1, 1)
  Board.reviveCell(2, 0)
  Board.reviveCell(3, 1)
  Board.reviveCell(3, 2)
  Board.reviveCell(3, 3)
  Board.reviveCell(1, 2)
  Board.reviveCell(1, 3)
  Board.reviveCell(2, 4)
  Board.switchColor(2, 0)

  println()
  Board.printBoard()
  println()

  for (i <- 0 until 10) {

    Board.nextGeneration()
    println()
    Board.printBoard()
    println()
    println()
    println(Board.cellsAlive())

  }

  Statistics.displayStatistics()

}
