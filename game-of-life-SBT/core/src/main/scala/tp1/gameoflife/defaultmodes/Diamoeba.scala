package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine


class Diamoeba (override val height: Int, override val width: Int) extends GameEngine{
  override def toString: String = "Diamoeba"

  override val description: String = "Diamoeba particles are peculiar indeed. They're like a half-way step between Amoeba and Assimilation particles, producing irregular diamond shapes that lean towards gently disappearing on their own. They can survive when surrounded by 5, 6, 7, or 8 particles, and come back to life if surrounded by 3, 5, 6, 7, or 8 particles. \nAn interesting rectangular pattern is created when creating a perpendicular straight line. "

  override val defaultColor: Color = new Color(0.58f, 0f, 0f, 1)

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 3 || aliveCount == 5 || aliveCount == 6 || aliveCount == 7 || aliveCount == 8)
      true

    else
      false
  }

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 5 || aliveCount == 6 || aliveCount == 7 || aliveCount == 8)
      true

    else
      false
  }
}
