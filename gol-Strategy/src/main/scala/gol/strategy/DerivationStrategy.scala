package gol.template

trait DerivationStrategy {
  def shouldKeepAlive(i: Int, j: Int): Boolean
  def shouldRevive(i: Int, j: Int): Boolean
}
