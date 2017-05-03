package tp1.gameoflife.controller

import com.badlogic.gdx.Game
import tp1.gameoflife.view.{DemoScreen, GameView}

object Controller extends Game {

  override def create() {
    this.setScreen(GameView.screen)
    GameView.update()
  }
}
