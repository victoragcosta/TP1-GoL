package gol.strategy


trait DerivationStrategy {
  def shouldKeepAlive(i: Int, j: Int): Boolean
  def shouldRevive(i: Int, j: Int): Boolean
}
