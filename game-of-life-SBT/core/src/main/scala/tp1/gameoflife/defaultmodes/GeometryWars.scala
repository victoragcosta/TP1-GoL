package tp1.gameoflife.defaultmodes

import tp1.gameoflife.gameengine.GameEngine

class GeometryWars (override val height: Int, override val width: Int) extends GameEngine {

  override def toString: String = "GeometryWars"

  override val description: String = "Sometimes there is beauty in geometric chaos. " +
    "A cell must be near 1, 2 or 5 cells to stay alive. " +
    "A dead cell revives if there are exactly 3 or 6 cells alive near it."

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 1 || aliveCount == 2 || aliveCount == 5)
      true

    else
      false

  }

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 3 || aliveCount == 6)
      true

    else
      false

  }

}
