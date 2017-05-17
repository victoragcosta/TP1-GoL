package tp1.gameoflife.controller

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.{Game, Gdx}
import tp1.gameoflife.gameengine.{GameEngine, Statistics}
import tp1.gameoflife.view.GameView

object GameController extends Game {

  private var ruleNumber: Int = 0
  private var gameMode: GameEngine = DependencyInjector.getRule(ruleNumber)

  def getGameModeName: String = gameMode.toString

  override def create() {
    this.setScreen(GameView.screen)
    Gdx.input.setInputProcessor(new GameInputHandler)
    GameView.calculatePadding(gameMode.width, gameMode.height)
    GameView.update(gameMode.currentGeneration, gameMode.width, gameMode.height)
  }

  def nextGeneration(): Unit = {
    gameMode.nextGeneration()
    GameView.update(gameMode.currentGeneration, gameMode.width, gameMode.height)
  }

  def previousGeneration(): Unit = {
    try{
      gameMode.undo()
      GameView.update(gameMode.currentGeneration, gameMode.width, gameMode.height)
    } catch {
      case _: Exception =>
    }
  }

  def makeAlive(x: Int, y: Int): Unit ={
    try{
      if(inBounds(x,y))
        gameMode.reviveCell(y,x)
      GameView.update(gameMode.currentGeneration, gameMode.width, gameMode.height)
    } catch {
      case _: Exception =>
    }
  }

  def killCell(x: Int, y: Int): Unit ={
    try{
      if(inBounds(x,y)){
        gameMode.killCell(y,x)
        Statistics.addDeath()
      }
      GameView.update(gameMode.currentGeneration, gameMode.width, gameMode.height)
    } catch {
      case _: Exception =>
    }
  }

  def killAll(): Unit ={
    for(i <- gameMode.currentGeneration.elements.indices)
      for(j <- gameMode.currentGeneration.elements(i).indices)
        killCell(j,i)
  }

  def endGame(): Unit ={
    Statistics.displayStatistics()
    Gdx.app.exit()
  }

  def changeAutoGenState(): Unit = {
    GameView.changeAutoGenState()
  }
  def pauseGame(): Unit = GameView.pauseGame()

  def changeRule(): Unit = {
    ruleNumber+=1
    if(ruleNumber >= DependencyInjector.classes.length){
      ruleNumber = 0
    }
    DependencyInjector.getRule(ruleNumber).currentGeneration = gameMode.currentGeneration
    gameMode = DependencyInjector.getRule(ruleNumber)
  }
  def changeRule(gameRule: GameEngine): Unit = {
    try{
      gameRule.currentGeneration = gameMode.currentGeneration
      gameMode = gameRule
    } catch {
      case e: Exception => println(e.getMessage)
    }
  }

  def switchColor(x: Int, y: Int): Color = {
    try{
      if(inBounds(x,y))
        gameMode.switchColor(y,x)
      gameMode.defaultColor
    } catch {
      case _: Exception =>
        gameMode.defaultColor
    }
  }

  def cellIsAlive(x: Int, y: Int): Boolean = {
    try {
      inBounds(x,y) && gameMode.isCellAlive(y,x)
    } catch {
      case _: Exception =>
        false
    }
  }

  private def inBounds(x: Int,y: Int): Boolean = 0 <= x && x < gameMode.width && 0 <= y && y < gameMode.height

  def speedUp(multiplier: Double): Unit ={
    GameView.speedUp(multiplier)
  }
  def speedDown(multiplier: Double): Unit ={
    GameView.speedDown(multiplier)
  }
  def speedReset(): Unit ={
    GameView.speedReset()
  }

}
