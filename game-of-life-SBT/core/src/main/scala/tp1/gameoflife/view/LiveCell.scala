package tp1.gameoflife.view

import com.badlogic.gdx.math.Vector3
import tp1.gameoflife.gameengine.Cell

class LiveCell(pos: Vector3, cell: Cell) {
  def x = pos.x
  def y = pos.y
  def alive = cell.alive
  def color = cell.color
}
