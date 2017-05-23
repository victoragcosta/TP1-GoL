package tp1.gameoflife.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import tp1.gameoflife.gameengine.Cell

/**
  * Classe simples pra guardar dados da célula e a posição
  * @param pos posição na tela
  * @param cell célula que se deve desenhar
  */
class LiveCell(pos: Vector2, cell: Cell) {
  def x: Float = pos.x
  def y: Float = pos.y
  def alive: Boolean = cell.alive
  def color: Color= cell.color
}
