package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine


class PseudoLife (override val height: Int, override val width: Int) extends GameEngine{
  override def toString: String = "PseudoLife"

  override val description: String = "Pseudo Life produces patterns that look superficially like normal GOL, hence its name. Particles of Pseudo Life survive if surrounded by 2, 3, or 8 particles, and come to life if surrounded by 3, 5, or 7 particles. "

  override val defaultColor: Color = new Color(0f, 0.4f, 1f, 1)

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 3 || aliveCount == 5 || aliveCount == 7)
      true

    else
      false
  }

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 2 || aliveCount == 3 || aliveCount == 8)
      true

    else
      false
  }

}
