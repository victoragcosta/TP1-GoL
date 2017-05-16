package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

class Seeds (override val height: Int, override val width: Int) extends GameEngine{

  override def toString: String = "Seeds"

  override val description: String = "An exploding rule in which every cell dies in every generation. It has many simple orthogonal spaceships, though it is in general difficult to create patterns that don't explode. "

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = false

  override val defaultColor = new Color(0.2f, 1f, 0f, 1)

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 2)
      true

    else
      false
  }
}
