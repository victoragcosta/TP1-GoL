package tp1.gameoflife.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

/**
  * Trait Que permite o desenho de imagens ao invés do nome em botões.
  */
trait Image {
  def heightImage: Int
  def widthImage: Int
  def generateImage(shapeRenderer: ShapeRenderer, x: Int, y: Int)
  def drawInside(shapeRenderer: ShapeRenderer)
}
