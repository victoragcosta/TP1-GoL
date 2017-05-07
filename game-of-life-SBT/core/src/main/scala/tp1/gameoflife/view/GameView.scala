package tp1.gameoflife.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector3
import tp1.gameoflife.controller.GameController
import tp1.gameoflife.gameengine.GameEngine

object GameView{

  final val minSquareSide: Float = 10
  var squareSide: Float = 10
  var vivas: List[Vector3] = _
  val screen = new GameScreen

  val scrW: Int = Gdx.graphics.getWidth
  val scrH: Int = Gdx.graphics.getHeight
  val menuH: Int = 50
  val menuW: Int = Gdx.graphics.getWidth
  var paddingW: Int = 0
  var paddingH: Int = 0

  val buttonH: Int = 30
  val buttonW: Int = 100
  val buttonPad: Int = 10
  val buttons: List[GameButton] = createButtons;arrangeButtons()

  def update(gameEngine: GameEngine): Unit = {
    calculatePadding(gameEngine)
    updateLiveCells(gameEngine)
  }

  private def createButtons: List[GameButton] ={
    var list: List[GameButton] = Nil
    list = new GameButton("Exit", _ => GameController.endGame())::list
    list = new GameButton("Change Rule", _ => println("oi"))::list
    list = new GameButton("Clear", _ => GameController.killAll())::list
    list = new GameButton("Next Gen", _ => GameController.nextGeneration())::list
    list = new GameButton("Start/Pause", _ => println("oi"))::list
    list = new GameButton("Prev Gen", _ => GameController.previousGeneration())::list
    list
  }

  private def arrangeButtons(): Unit ={
    val buttonPadSides = (menuW - (buttons.size*buttonW + (buttons.size-1)*buttonPad))/2
    val buttonPadTopBot = (menuH - buttonH)/2
    for(i <- buttons.indices){
      val gameButton = buttons(i)
      gameButton.setPosition(buttonPadSides + i*(buttonW+buttonPad), buttonPadTopBot)
    }
  }

  def highlight(button: Int, on: Boolean): Unit ={
    buttons(button).setHighlight(on)
  }

  private def calculatePadding(gameEngine: GameEngine) = {
    val w = gameEngine.width
    val h = gameEngine.height
    val sqrSideW = scrW / w
    val sqrSideH = (scrH - menuH) / h
    if (sqrSideW < 10 || sqrSideH < 10)
      throw new Exception("Este tamanho de tabuleiro não cabe na tela")
    if (sqrSideW < sqrSideH) {
      squareSide = sqrSideW
    }
    else {
      squareSide = sqrSideH
    }
    paddingW = ((scrW - squareSide * w) / 2).toInt
    paddingH = ((scrH - menuH - squareSide * h) / 2).toInt
  }

  private def updateLiveCells(gameEngine: GameEngine) = {
    vivas = Nil
    for(h <- 0 until gameEngine.height){
      for(w <- 0 until gameEngine.width){
        if(gameEngine.currentGeneration.elements(h)(w).alive)
          vivas = new Vector3(w*squareSide,h*squareSide,0)::vivas
      }
    }
  }


}
