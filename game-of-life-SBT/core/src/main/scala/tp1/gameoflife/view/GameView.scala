package tp1.gameoflife.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector3
import tp1.gameoflife.gameengine.GameEngine

object GameView{

  val squareSide = 10
  var vivas: List[Vector3] = _

  def update(gameEngine: GameEngine) = {
    updateLiveCells(gameEngine)
  }

  private def updateLiveCells(gameEngine: GameEngine) = {
    vivas = null
    var y = 0
    for(h <- 0 to gameEngine.height){
      var x = 0
      for(w <- 0 to gameEngine.width){
        if(gameEngine.currentGeneration.elements(h)(w).alive)
          vivas = new Vector3(w,h,0)::vivas
        x += squareSide
      }
      y += squareSide
    }
  }

  val screen = new GameScreen
}
