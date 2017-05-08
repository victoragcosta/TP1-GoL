package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color

object TestEnvironment extends App{

  val Board = new ImmigrantLife(10, 10)

  println(Board.toString)

  print(Board.description)

  Board.currentGeneration.elements(1)(1).switchState()
  Board.currentGeneration.elements(1)(2).switchState()
  Board.currentGeneration.elements(1)(3).switchState()
  Board.currentGeneration.elements(2)(3).switchState()
  Board.currentGeneration.elements(3)(2).switchState()
  Board.currentGeneration.elements(1)(1).color = new Color(0, 0, 1, 0.9f)
  Board.currentGeneration.elements(1)(2).color = new Color(1, 0, 0, 0.9f)
  Board.currentGeneration.elements(1)(3).color = new Color(0, 0, 1, 0.9f)
  Board.currentGeneration.elements(2)(3).color = new Color(1, 0, 0, 0.9f)
  Board.currentGeneration.elements(3)(2).color = new Color(0, 0, 1, 0.9f)

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
