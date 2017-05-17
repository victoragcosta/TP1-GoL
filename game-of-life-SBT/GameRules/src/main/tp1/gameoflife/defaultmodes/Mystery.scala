package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

class Mystery (override val height: Int, override val width: Int) extends GameEngine {
  override def toString: String = "Mystery"

  override val description: String = "Similar to diamoeba. \nIt is the only life variant in TPT that can survive with 0 surrounding cells."

  override val defaultColor: Color = new Color(0f, 0f, 0.78f, 1)

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 3 || aliveCount == 4 || aliveCount == 5 || aliveCount == 8)
      true

    else
      false
  }

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 0 || aliveCount == 5 || aliveCount == 6 || aliveCount == 7 )
      true

    else
      false
  }

}
