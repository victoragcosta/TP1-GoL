package tp1.gameoflife.defaultmodes

import java.util.Calendar

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine

import scala.util.Random

class Medieval (override val height: Int, override val width: Int) extends GameEngine {

  override def toString: String = "Medieval"

  override val description: String = "Is this a little overkill? Yes! But, so much fun! " +
    "There are three types of cells: Knights (Grey), Assassins (Brown) and Archers (Green). " +
    "A cell must be near 2 or 3 cells to stay alive. " +
    "A dead cell revives if there are exactly 3 cells alive near it. " +
    "The color of the revived cell is determined by the color of the majority of cells around it. " +
    "If there is a tie, the color is determined randomly. " +
    "Knights kill Assassins, Assassins kill Archers and Archers kill Knights."

  override val defaultColor = new Color(0.4f, 0.4f, 0.4f, 1)

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 2 || aliveCount == 3) {

      if (this.currentGeneration.elements(cellHeight)(cellWidth).color == defaultColor) {

        if (archerCount(cellHeight, cellWidth) == 0)
          true

        else
          false

      }

      else if (this.currentGeneration.elements(cellHeight)(cellWidth).color == new Color(0.6f, 0.3f, 0, 0.9f)) {

        if (knightCount(cellHeight, cellWidth) == 0)
          true

        else
          false

      }

      else {

        if (assassinCount(cellHeight, cellWidth) == 0)
          true

        else
          false

      }

    }

    else
      false

  }

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = neighborsAlive(cellHeight, cellWidth)

    if (aliveCount == 3)
      true

    else
      false

  }

  override def determineCellColor(cellHeight: Int, cellWidth: Int): Color = {

    val knights: Int = knightCount(cellHeight, cellWidth)
    val assassins: Int = assassinCount(cellHeight, cellWidth)
    val archers: Int = archerCount(cellHeight, cellWidth)

    Random.setSeed(Calendar.getInstance.getTimeInMillis)

    val tieBreaker = Random.nextInt() % 3
    val majority = math.max(math.max(knights, assassins), archers)

    if (knights == assassins && knights == archers) {

      if (tieBreaker == 0)
        new Color(0, 0.4f, 0, 0.9f)

      else if (tieBreaker == 1)
        new Color(0.6f, 0.3f, 0, 0.9f)

      else
        defaultColor

    }

    else {

      if (majority == knights)
        defaultColor

      else if (majority == assassins)
        new Color(0.6f, 0.3f, 0, 0.9f)

      else
        new Color(0, 0.4f, 0, 0.9f)

    }

  }

  override def switchColor (cellHeight: Int, cellWidth: Int): Unit = {

    if (this.currentGeneration.elements(cellHeight)(cellWidth).alive) {

      if (this.currentGeneration.elements(cellHeight)(cellWidth).color == defaultColor)
        this.currentGeneration.elements(cellHeight)(cellWidth).color = new Color(0.6f, 0.3f, 0, 0.9f)

      else if (this.currentGeneration.elements(cellHeight)(cellWidth).color == new Color(0.6f, 0.3f, 0, 0.9f))
        this.currentGeneration.elements(cellHeight)(cellWidth).color = new Color(0, 0.4f, 0, 0.9f)

      else
        this.currentGeneration.elements(cellHeight)(cellWidth).color = defaultColor

    }

  }

  def knightCount (cellHeight: Int, cellWidth: Int): Int = {

    var knightCount: Int = 0

    for (i <- -1 to 1) {
      for (j <- -1 to 1) {

        if (this.currentGeneration.elements(adjustHeight(cellHeight + i))(adjustWidth(cellWidth + j)).color ==
          defaultColor)
          knightCount += 1

      }
    }

    if (this.currentGeneration.elements(adjustHeight(cellHeight))(adjustWidth(cellWidth)).color ==
      defaultColor)
      knightCount -= 1

    knightCount

  }

  def assassinCount (cellHeight: Int, cellWidth: Int): Int = {

    var assassinCount: Int = 0

    for (i <- -1 to 1) {
      for (j <- -1 to 1) {

        if (this.currentGeneration.elements(adjustHeight(cellHeight + i))(adjustWidth(cellWidth + j)).color ==
          new Color(0.6f, 0.3f, 0, 0.9f))
          assassinCount += 1

      }
    }

    if (this.currentGeneration.elements(adjustHeight(cellHeight))(adjustWidth(cellWidth)).color ==
      new Color(0.6f, 0.3f, 0, 0.9f))
      assassinCount -= 1

    assassinCount

  }

  def archerCount (cellHeight: Int, cellWidth: Int): Int = {

    var archerCount: Int = 0

    for (i <- -1 to 1) {
      for (j <- -1 to 1) {

        if (this.currentGeneration.elements(adjustHeight(cellHeight + i))(adjustWidth(cellWidth + j)).color ==
          new Color(0, 0.4f, 0, 0.9f))
          archerCount += 1

      }
    }

    if (this.currentGeneration.elements(adjustHeight(cellHeight))(adjustWidth(cellWidth)).color ==
      new Color(0, 0.4f, 0, 0.9f))
      archerCount -= 1

    archerCount

  }

}
