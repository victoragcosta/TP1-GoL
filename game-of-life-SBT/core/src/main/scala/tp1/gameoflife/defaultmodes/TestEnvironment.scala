package tp1.gameoflife.defaultmodes

object TestEnvironment extends App{

  val Board = new Classic(10, 10)

  println()
  Board.printBoard()
  println()

  Board.currentGeneration.elements(4)(3).alive = true
  Board.currentGeneration.elements(4)(4).alive = true
  Board.currentGeneration.elements(4)(5).alive = true

  println()
  Board.printBoard()
  println()

  Board.nextGeneration()

  println()
  Board.printBoard()
  println()

}
