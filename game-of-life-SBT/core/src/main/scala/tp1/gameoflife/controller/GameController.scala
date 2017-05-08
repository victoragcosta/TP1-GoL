package tp1.gameoflife.controller

import com.badlogic.gdx.{Game, Gdx}
import tp1.gameoflife.gameengine.GameEngine
import tp1.gameoflife.view.GameView

object GameController extends Game {

  private var ruleNumber: Int = 0
  private var gameMode: GameEngine = RulesVault.list(ruleNumber)

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
      gameMode.makeAlive(x,y)
      GameView.update(gameMode)
    } catch {
      case e: Exception => println(e.getLocalizedMessage)
    }
  }

  def killCell(x: Int, y: Int): Unit ={
    try{
      gameMode.killCell(x,y)
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

  def changeRule(name: String): Unit = {
    val newRule = RulesVault.find(name)
    newRule match {
      case Some(rule) =>
        rule.currentGeneration = gameMode.currentGeneration
        gameMode = rule
      case None => println("Num existe mano")
    }
  }

  def changeRule(): Unit = {
    ruleNumber+=1
    if(ruleNumber >= RulesVault.list.length){
      ruleNumber = 0
    }
    RulesVault.list(ruleNumber).currentGeneration = gameMode.currentGeneration
    gameMode = RulesVault.list(ruleNumber)
    println(gameMode)
  }

}
