package tp1.gameoflife.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.{Vector2, Vector3}
import tp1.gameoflife.controller.GameController
import tp1.gameoflife.gameengine.{GameEngine, Table}

object GameView{

  final val minSquareSide: Float = 4
  var squareSide: Float = 10
  var vivas: List[LiveCell] = _
  val screen = new GameScreen

  var oldW: Int = 0
  var oldH: Int = 0

  val scrW: Int = Gdx.graphics.getWidth
  val scrH: Int = Gdx.graphics.getHeight
  val menuH: Int = 50
  val menuW: Int = Gdx.graphics.getWidth
  var paddingW: Int = 0
  var paddingH: Int = 0

  private val buttonH: Int = 30
  private val buttonW: Int = 100
  private val buttonPad: Int = 10
  val buttons: List[GameButton] = createButtons;arrangeButtons()

  var paused = false
  val defaultSpeed = 100
  var delay = defaultSpeed

  var menuOpen = false

  def update(cells: Table, w: Int, h: Int): Unit = {
    if(w != oldW){
      oldW = w
      calculatePadding(w,h)
    }
    if(h != oldH){
      oldH = h
      calculatePadding(w, h)
    }
    updateLiveCells(cells, w, h)
  }

  private def createButtons: List[GameButton] = {
    var list: List[GameButton] = Nil
    list = new GameButton("Exit", _ => GameController.endGame())::list
    list = new MenuButton(GameController.getGameModeName)::list
    list = new GameButton("Clear", _ => GameController.killAll())::list
    val speedDisplay = new GameButton("Speed: 1.0", b => {
      GameController.speedReset()
      val speed = GameView.defaultSpeed/GameView.delay.toDouble
      b.setName(f"Speed: $speed%.2f")
    })
    list = new GameButton("Speed Up", _ => {
      GameController.speedUp(10)
      val speed = GameView.defaultSpeed/GameView.delay.toDouble
      speedDisplay.setName(f"Speed: $speed%.2f")
    })::list
    list = speedDisplay::list
    list = new GameButton("Speed Down", _ => {
      GameController.speedDown(10)
      val speed = GameView.defaultSpeed/GameView.delay.toDouble
      speedDisplay.setName(f"Speed: $speed%.2f")
    })::list
    list = new GameButton("Next Gen", _ => GameController.nextGeneration())::list
    list = new PlayButton("Start/Pause", b => {
      GameController.changeAutoGenState()
    })::list
    list = new GameButton("Prev Gen", _ => GameController.previousGeneration())::list
    list
  }

  private def arrangeButtons(): Unit ={
    val buttonPadSides = (menuW - (buttons.size*buttonW + (buttons.size-1)*buttonPad))/2
    val buttonPadTopBot = (menuH - buttonH)/2
    for(i <- buttons.indices){
      val gameButton = buttons(i)
      gameButton.setPosition1(buttonPadSides + i*(buttonW+buttonPad), buttonPadTopBot)
      gameButton.setPosition2(buttonPadSides + i*(buttonW+buttonPad)+buttonW, buttonPadTopBot+buttonH)
      gameButton match{
        case m: MenuButton =>
          m.setBLCorner(10, menuH+10)
          m.setTRCorner(scrW-10, scrH-10)
        case _ =>
      }
    }
  }

  def highlight(button: Int, on: Boolean): Unit = buttons(button).setHighlight(on)

  def calculatePadding(w: Int, h: Int) = {
    val sqrSideW = scrW / w
    val sqrSideH = (scrH - menuH) / h
    if (sqrSideW < minSquareSide || sqrSideH < minSquareSide)
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

  private def updateLiveCells(cells: Table, w: Int, h: Int) = {
    vivas = Nil
    for(h <- 0 until h){
      for(w <- 0 until w){
        val cell = cells.elements(h)(w)
        if(cell.alive || cell.afterLife)
          vivas = new LiveCell(new Vector2(w*squareSide,h*squareSide), cell)::vivas
      }
    }
  }

  def changeAutoGenState(): Unit = {
    if(!menuOpen){
      paused = !paused
      buttons.foreach {
        case p: PlayButton => p.changeState()
        case _ =>
      }
    }
  }
  def pauseGame(): Unit = {
    if(!paused)
      changeAutoGenState()
  }

  def speedUp(multiplier: Double): Unit ={
    if(multiplier > 1){
      if(delay-multiplier >= 10)
        delay = (delay-multiplier).toInt
      else
        delay = 10
    }
  }
  def speedDown(multiplier: Double): Unit ={
    if(multiplier > 1){
      if(delay+multiplier <= 2000)
        delay = (delay+multiplier).toInt
      else
        delay = 2000
    }
  }
  def speedReset(): Unit ={
    delay = defaultSpeed
  }

}
