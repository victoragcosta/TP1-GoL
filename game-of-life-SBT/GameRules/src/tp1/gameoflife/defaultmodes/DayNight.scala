package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine


class DayNight (override val height: Int, override val width: Int) extends GameEngine{
  override def toString: String = "Day and Night"

  override val description: String = "Day and Night particles survive if surrounded by 3, 4, 6, 7, or 8 particles, and come to life if surrounded by 3, 6, 7, or 8 particles. The result is an exotic particle who's physical properties are exactly inversed both in floodfilled areas and empty ones. In other words, the same patterns can appear inside of a solid block of Day and Night as outside of it, hence its name. "

  override val defaultColor: Color = new Color(0.59f, 0.84f, 1f, 1)

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 3 || aliveCount == 6 || aliveCount == 7 || aliveCount == 8)
      true

    else
      false
  }

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 3 || aliveCount == 4 || aliveCount == 5 || aliveCount == 7 || aliveCount == 8)
      true

    else
      false
  }
}

