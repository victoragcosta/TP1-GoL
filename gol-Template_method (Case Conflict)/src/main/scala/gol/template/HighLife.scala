package gol.template

/**
  * Created by hugon on 12-May-17.
  */
class HighLife extends GameEngine{

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
