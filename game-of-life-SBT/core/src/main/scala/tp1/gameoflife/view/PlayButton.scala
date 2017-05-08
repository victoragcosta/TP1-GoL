package tp1.gameoflife.view
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.Vector2

class PlayButton(
                _namey: String,
                _action: (GameButton) => Unit,
                _color: Color = new Color(0,0,0.5f,1),
                _colorHighlighted: Color = new Color(0,0,0.9f,1),
                _colorFont: Color = Color.WHITE,
                _colorFontHighlighted: Color = Color.BLACK
                ) extends GameButton(
                _namey,
                _action,
                _color,
                _colorHighlighted,
                _colorFont,
                _colorFontHighlighted
                ) with Image {

  var paused = false

  override def height: Int = 12

  override def width: Int = if(paused) 12 else 9

  def changeState(): Unit = paused = !paused

  override def generateImage(shapeRenderer: ShapeRenderer, x: Int, y: Int): Unit = {

    paused match{
      case true =>
        val p1 = new Vector2(x,y)
        val p2 = new Vector2(x,y+12)
        val p3 = new Vector2(x+12,y+6)

        shapeRenderer.triangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y)

      case _ =>
        shapeRenderer.rect(x, y, 3, 12)
        shapeRenderer.translate(6,0,0)
        shapeRenderer.rect(x, y, 3, 12)
        shapeRenderer.translate(-6,0,0)
    }

  }
}
