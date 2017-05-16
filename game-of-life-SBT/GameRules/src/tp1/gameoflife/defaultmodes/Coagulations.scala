package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

/**
  * Created by hugon on 13-May-17.
  */
class Coagulations (override val height: Int, override val width: Int) extends GameEngine {
  override def toString: String = "Coagulations"

  override val description: String = "An exploding rule in which patterns tend to expand forever, producing a thick \"goo\" as it does so."

  override val defaultColor: Color = new Color(0f, 0f, 0.78f, 1)

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 3 || aliveCount == 7 || aliveCount == 8)
      true

    else
      false
  }

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {
    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 2 || aliveCount == 3 || aliveCount == 5 || aliveCount == 6 || aliveCount == 7 || aliveCount == 8)
      true

    else
      false
  }

}
