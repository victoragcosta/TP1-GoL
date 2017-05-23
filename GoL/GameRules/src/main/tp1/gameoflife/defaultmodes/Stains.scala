package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

/**
  * Created by hugon on 13-May-17.
  */
class Stains (override val height: Int, override val width: Int) extends GameEngine{
  override def toString: String = "Stains"

  override val description: String = "Stains particles are an exploding-type pattern that tends to splash outwards from its point of origin in predictable patterns in solid fill. Stain particles survive when surrounded by 2, 3, 5, 6, 7, or 8 particles, and come back to life when surrounded by 3, 6, 7, or 8 particles. "

  override val defaultColor: Color = new Color(0.74f, 0.56f, 0.3f, 1)

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 3 || aliveCount == 6 || aliveCount == 7 || aliveCount == 8)
      true

    else
      false
  }

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 2 || aliveCount == 3 || aliveCount == 5 || aliveCount == 6 || aliveCount == 7 || aliveCount == 8)
      true

    else
      false
  }

}
