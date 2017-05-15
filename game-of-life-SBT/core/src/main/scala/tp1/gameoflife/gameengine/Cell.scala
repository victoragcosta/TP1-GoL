package tp1.gameoflife.gameengine

import com.badlogic.gdx.graphics.Color

class Cell (var alive: Boolean, var color: Color) {

  var afterLife: Boolean = false
  var afterLifeCount: Int = 0

  def switchState(): Unit = {

    if(this.alive)
      this.alive = false

    else
      this.alive = true

  }

}