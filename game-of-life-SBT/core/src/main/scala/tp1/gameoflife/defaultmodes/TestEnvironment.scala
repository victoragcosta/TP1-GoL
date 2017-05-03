package tp1.gameoflife.defaultmodes

object TestEnvironment extends App{

  val Board = new Classic(7, 7)

  Board.currentGeneration.elements(1)(2).alive = true
  Board.currentGeneration.elements(2)(2).alive = true
  Board.currentGeneration.elements(3)(2).alive = true
  Board.currentGeneration.elements(3)(1).alive = true
  Board.currentGeneration.elements(2)(0).alive = true

  println()
  Board.printBoard()
  println()

  Board.nextGeneration()
  Board.undo()

  println()
  Board.printBoard()
  println()

}
