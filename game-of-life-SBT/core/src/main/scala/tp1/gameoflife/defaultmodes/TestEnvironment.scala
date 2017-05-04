package tp1.gameoflife.defaultmodes

object TestEnvironment extends App{

  val Board = new HighLife(30, 30)

  Board.currentGeneration.elements(11)(13).switchState()
  Board.currentGeneration.elements(11)(14).switchState()
  Board.currentGeneration.elements(11)(15).switchState()
  Board.currentGeneration.elements(12)(12).switchState()
  Board.currentGeneration.elements(12)(15).switchState()
  Board.currentGeneration.elements(13)(11).switchState()
  Board.currentGeneration.elements(13)(15).switchState()
  Board.currentGeneration.elements(14)(11).switchState()
  Board.currentGeneration.elements(14)(14).switchState()
  Board.currentGeneration.elements(15)(11).switchState()
  Board.currentGeneration.elements(15)(12).switchState()
  Board.currentGeneration.elements(15)(13).switchState()

  println()
  Board.printBoard()
  println()

  for (i <- 0 until 12)
    Board.nextGeneration()

  println()
  Board.printBoard()
  println()

  for (i <- 0 until 12)
    Board.nextGeneration()

  println()
  Board.printBoard()
  println()

}
