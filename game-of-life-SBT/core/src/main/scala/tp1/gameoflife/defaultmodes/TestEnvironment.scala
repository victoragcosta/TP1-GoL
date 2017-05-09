package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color

object TestEnvironment extends App{

  val Board = new Maze(10, 10)

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
  Board.reviveCell(5, 1)
  Board.reviveCell(5, 2)

  println()
  Board.printBoard()
  println()

  for (i <- 0 until 5) {

    Board.nextGeneration()
    println()
    Board.printBoard()
    println()

  }

}
