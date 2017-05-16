package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

class Assimilation (override val height: Int, override val width: Int) extends GameEngine{

  override def toString: String = "Assimilation"

  override val description: String = "Assimilation particles survive if surrounded by 4, 5, 6, or 7 particles, and come to life if surrounded by 3, 4, or 5 particles. The result is a diamond-generating ruleset that can usually perfectly regenerate into its original shape if a large portion is deleted. Diamonds of ASIM can also absorb \"fizzy\" patterns, which makes them larger."

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 4 || aliveCount == 5 || aliveCount == 6 || aliveCount == 7)
      true

    else
      false
  }

  override val defaultColor = new Color(0f, 0f, 0f, 1)

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 3 || aliveCount == 4 || aliveCount == 5)
      true

    else
      false
  }

}
