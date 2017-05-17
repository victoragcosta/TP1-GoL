package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

class Fractals (override val height: Int, override val width: Int) extends GameEngine{

  override def toString: String = "Fractals"

  override val description: String = "The automaton 12/1 generates four very close approximations to the Sierpiński triangle when applied to a single live cell. "

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 1 || aliveCount == 2)
      true

    else
      false
  }

  override val defaultColor = new Color(0f, 0f, 0f, 1)

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 1)
      true

    else
      false
  }

}
