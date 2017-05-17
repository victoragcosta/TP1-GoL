package gol.template

/**
  * Created by hugon on 12-May-17.
  */
class Classic extends GameEngine{

  override def toString: String = "Classic"

  override def shouldKeepAlive(i: Int, j: Int): Boolean = {
    cells(i)(j).isAlive &&
      (numberOfNeighborhoodAliveCells(i, j) == 2 || numberOfNeighborhoodAliveCells(i, j) == 3)
  }

  override def shouldRevive(i: Int, j: Int): Boolean = {
    (!cells(i)(j).isAlive) &&
      (numberOfNeighborhoodAliveCells(i, j) == 3)
  }
}
