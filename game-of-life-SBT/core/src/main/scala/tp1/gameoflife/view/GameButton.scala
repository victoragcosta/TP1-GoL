package tp1.gameoflife.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

class GameButton(
                  var _name: String,
                  _action: (GameButton) => Unit,
                  _color: Color = new Color(0,0,0.5f,1),
                  _colorHighlighted: Color = new Color(0,0,0.9f,1),
                  _colorFont: Color = Color.WHITE,
                  _colorFontHighlighted: Color = Color.BLACK
                ){

  def name: String = _name
  def setName(namey: String) = _name = namey
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

  def action(): Unit = _action.apply(this)
}
