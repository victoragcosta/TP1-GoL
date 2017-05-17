package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

class Amoeba (override val height: Int, override val width: Int) extends GameEngine{

  override def toString: String = "Amoeba"

  override val description: String = "Amoeba particles live up to their name, sloshing around figuratively speaking,"+
  "producing a peculiar mess of splashy patterns. The result is what looks surprisingly like... Amoebas."+
  "One cool property demonstrated by amoeba is that whenever one draws a perfectly straight light form left to right, or up to down, a fractal traingle is produced."

  override val defaultColor = new Color(0.5f, 0.5f, 0.5f, 1)

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int) : Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 1 || aliveCount == 3 || aliveCount == 5 || aliveCount == 8)
      true

    else
      false
  }

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 3 || aliveCount == 5 || aliveCount == 7)
      true

    else
      false
  }
}
