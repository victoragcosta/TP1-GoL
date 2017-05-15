package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

/**
  * Created by hugon on 13-May-17.
  */
class Move (override val height : Int, override val width: Int) extends GameEngine{

  override def toString: String = "Move"

  override val description: String = "Move particles don't move anything... besides themselves. They survive if surrounded by 2, 4, or 5 particles, and come to life if surrounded by 3, 6 or 8 particles. The result is a particle that makes patterns that are adept at... moving. The patterns its makes tend to die of rather quickly from rand.om soup, however"

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 2 || aliveCount == 4 || aliveCount == 5)
      true

    else
      false
  }

  override val defaultColor = new Color(0.52f, 0f, 0.52f, 1)

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 3 || aliveCount == 6 || aliveCount == 8)
      true

    else
      false
  }

}
