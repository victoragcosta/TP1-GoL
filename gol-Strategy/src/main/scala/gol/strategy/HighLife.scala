package gol.strategy

import gol.strategy.GameEngine.numberOfNeighborhoodAliveCells
import gol.template.DerivationStrategy

class HighLife () extends DerivationStrategy {

  override def toString: String = "HighLife"

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = numberOfNeighborhoodAliveCells(cellHeight, cellWidth)

    if (aliveCount == 2 || aliveCount == 3)
      true

    else
      false

  }

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = {

    val aliveCount: Int = numberOfNeighborhoodAliveCells(cellHeight, cellWidth)

    if (aliveCount == 3 || aliveCount == 6)
      true

    else
      false

  }

}