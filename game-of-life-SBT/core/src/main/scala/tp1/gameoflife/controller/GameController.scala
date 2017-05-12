package tp1.gameoflife.controller

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.{Game, Gdx}
import tp1.gameoflife.gameengine.{GameEngine, Statistics}
import tp1.gameoflife.view.GameView
import tp1.gameoflife.main.Main

object GameController extends Game {

  private var ruleNumber: Int = 0
  private var gameMode: GameEngine = Main.getRule(0)

  def getGameModeName = gameMode.toString

  override def create() {
    this.setScreen(GameView.screen)
    GameView.update(gameMode)
  }

  def nextGeneration(): Unit = {
    gameMode.nextGeneration()
    GameView.update(gameMode)
  }

  def previousGeneration(): Unit = {
    try{
      gameMode.undo()
      GameView.update(gameMode)
    } catch {
      case e: Exception => println(e.getLocalizedMessage)
    }
  }

  def makeAlive(x: Int, y: Int): Unit ={
    try{
      if(inBounds(x,y)){
        gameMode.reviveCell(y,x)

      }
      GameView.update(gameMode)
    } catch {
      case e: Exception => println(e.getLocalizedMessage)
    }
  }

  def killCell(x: Int, y: Int): Unit ={
    try{
      if(inBounds(x,y)){
        gameMode.killCell(y,x)
        Statistics.addDeath()
      }
      GameView.update(gameMode)
    } catch {
      case e: Exception => println(e.getLocalizedMessage)
    }
  }

  def killAll(): Unit ={
    for(i <- gameMode.currentGeneration.elements.indices)
      for(j <- gameMode.currentGeneration.elements(i).indices)
        killCell(j,i)
  }

  def endGame(): Unit ={
    Gdx.app.exit()
  }

  def changeState(): Unit = {
    GameView.changeState()
  }

  def changeRule(): Unit = {
    ruleNumber+=1
    if(ruleNumber >= Main.classes.length){
      ruleNumber = 0
    }
    Main.getRule(ruleNumber).currentGeneration = gameMode.currentGeneration
    gameMode = Main.getRule(ruleNumber)
    println(gameMode)
  }

  def switchColor(x: Int, y: Int): Color = {
    try{
      if(inBounds(x,y))
        gameMode.switchColor(y,x)
      gameMode.defaultColor
    } catch {
      case e: Exception =>
        println(e.getMessage)
        gameMode.defaultColor
    }
  }

  def cellIsAlive(x: Int, y: Int): Boolean = {
    try {
      inBounds(x,y) && gameMode.isCellAlive(y,x)
    } catch {
      case e: Exception =>
        println(e.getMessage)
        false
    }
  }

  private def inBounds(x: Int,y: Int): Boolean = 0 <= x && x < gameMode.width && 0 <= y && y < gameMode.height

}
