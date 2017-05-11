package tp1.gameoflife.gameengine

import com.badlogic.gdx.graphics.Color

class Cell (var alive: Boolean, var color: Color) {

  def switchState(): Unit = {

    if(this.alive)
      this.alive = false

    else
      this.alive = true

  }

}