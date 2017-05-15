package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

class Life34 (override val height: Int, override val width: Int) extends GameEngine{

  override def toString: String = "34"

  override val description: String = "34 Life particles are an exploding-type particle that can be used to make certain types of useful patterns, but tend towards exploding randomly bigger and bigger if made much larger. 34 Life particles survive if surrounded by 3 or 4 particles, and come to life if surrounded by 3 or 4 particles (hence its name.)"

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 3 || aliveCount == 4)
      true

    else
      false
  }

  override val defaultColor = new Color(1f, 0.52f, 0f, 1)

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 3 || aliveCount == 4)
      true

    else
      false
  }
}
