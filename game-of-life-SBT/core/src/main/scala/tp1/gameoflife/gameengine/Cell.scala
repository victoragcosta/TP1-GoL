package tp1.gameoflife.gameengine

class Cell (var alive: Boolean) {

  def switchState(): Unit = {

    if(this.alive)
      this.alive = false

    else
      this.alive = true

  }

}