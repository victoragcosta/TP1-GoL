package tp1.gameoflife.pkg

import com.badlogic.gdx.Game

class GameOfLife extends Game {
    override def create() {
        this.setScreen(new DemoScreen)
    }
}
