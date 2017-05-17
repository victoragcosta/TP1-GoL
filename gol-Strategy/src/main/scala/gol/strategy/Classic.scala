package gol.strategy

import gol.strategy.GameEngine.numberOfNeighborhoodAliveCells
import gol.template.DerivationStrategy

class Classic extends DerivationStrategy{

  override def toString: String = "Classic"

  override def shouldKeepAlive(i: Int, j: Int): Boolean = {
    GameEngine.cells(i)(j).isAlive &&
      (numberOfNeighborhoodAliveCells(i, j) == 2 || numberOfNeighborhoodAliveCells(i, j) == 3)
  }

  override def shouldRevive(i: Int, j: Int): Boolean = {
    (!GameEngine.cells(i)(j).isAlive) &&
      (numberOfNeighborhoodAliveCells(i, j) == 3)
  }
}