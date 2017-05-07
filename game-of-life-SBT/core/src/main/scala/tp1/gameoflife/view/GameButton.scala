package tp1.gameoflife.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

class GameButton(_name: String, _action: (Unit) => Unit, _color: Color, _colorHighlighted: Color, _colorFont: Color, _colorFontHighlighted: Color){

  def this(_name: String, _action: (Unit) => Unit, _color: Color, _colorHighlighted: Color) = {
    this(_name, _action, _color, _colorHighlighted, Color.WHITE, Color.BLACK)
  }

  def this(_name: String, _action: (Unit) => Unit)={
    this(_name, _action, new Color(0,0,0.5f,1), new Color(0,0,0.9f,1))
  }

  def name: String = _name
  def color: Color = _color
  def colorHighlighted: Color = _colorHighlighted
  def colorFont: Color = _colorFont
  def colorFontHighlighted: Color = _colorFontHighlighted

  var pos: Vector2 = _

  def setPosition(x: Int, y: Int): Unit ={
    pos = new Vector2(x,y)
  }

  var highlight: Boolean = false

  def setHighlight(state: Boolean): Unit ={
    highlight = state
  }

  def action(): Unit = _action.apply()
}
