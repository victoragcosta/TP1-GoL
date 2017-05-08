package tp1.gameoflife.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

trait Image {
  def height: Int
  def width: Int
  def generateImage(shapeRenderer: ShapeRenderer, x: Int, y: Int)
}
