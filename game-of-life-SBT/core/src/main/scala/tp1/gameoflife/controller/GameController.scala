package tp1.gameoflife.controller

import com.badlogic.gdx.{Game, Gdx}
import tp1.gameoflife.defaultmodes.{Classic, HighLife}
import tp1.gameoflife.view.{DemoScreen, GameView}

object GameController extends Game {

  var gameMode = new Classic(53,80)

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
}
