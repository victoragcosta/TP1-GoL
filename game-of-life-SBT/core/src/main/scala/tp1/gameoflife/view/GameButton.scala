package tp1.gameoflife.view

import java.util.Calendar

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

class GameButton(
                  var _name: String,
                  _action: (GameButton) => Unit,
                  var _color: Color = new Color(0,0,0.5f,1),
                  var _colorHighlighted: Color = new Color(0,0,0.9f,1),
                  _colorFont: Color = Color.WHITE,
                  _colorFontHighlighted: Color = Color.BLACK
                ){

  def name: String = _name
  def setName(namey: String): Unit = _name = namey
  def color: Color = _color
  def colorHighlighted: Color = _colorHighlighted
  def colorFont: Color = _colorFont
  def colorFontHighlighted: Color = _colorFontHighlighted

  var pos1: Vector2 = _
  var pos2: Vector2 = _

  def setPosition1(x: Int, y: Int): Unit ={
    pos1 = new Vector2(x,y)
  }

  def setPosition2(x: Int, y: Int): Unit ={
    pos2 = new Vector2(x,y)
  }

  def width: Int = (pos2.x - pos1.x).toInt
  def height: Int = (pos2.y - pos1.y).toInt

  var highlight: Boolean = false

  def setHighlight(state: Boolean): Unit ={
    highlight = state
  }

  def setColor(color: Color): Unit ={
    this._color = color
    this._colorHighlighted = new Color(color.r+0.4f, color.g+0.4f, color.b+0.4f, 1)
  }

  private var debounce = false
  private var lastTime = Calendar.getInstance().getTimeInMillis
  private val delay = 50
  def action(): Unit = {
    val now = Calendar.getInstance().getTimeInMillis
    if(now > lastTime + delay){
      debounce = false
      lastTime = now
    }

    if(!debounce){
      _action.apply(this)
      debounce = true
    }
  }

  def padW(font: BitmapFont): Int = ((this.width - font.getBounds(this.name).width)/2).toInt
  def padH(font: BitmapFont): Int = ((this.height - font.getBounds(this.name).height)/2).toInt

  def drawName(font: BitmapFont, fontBatch: SpriteBatch): Unit ={
    if(highlight){
      font.setColor(colorFontHighlighted)
    } else {
      font.setColor(colorFont)
    }
    font.draw(fontBatch, name, pos1.x + padW(font), pos2.y - padH(font))
  }

  def drawButton(shapeRenderer: ShapeRenderer): Unit ={
    if(highlight) shapeRenderer.setColor(colorHighlighted)
    else shapeRenderer.setColor(color)
    shapeRenderer.rect(pos1.x, pos1.y, width, height)
  }

}
