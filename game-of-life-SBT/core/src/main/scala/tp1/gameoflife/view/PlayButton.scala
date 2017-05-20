package tp1.gameoflife.view
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2


/**
  * Implementa um botão de play que mostra os simbolos universais de play e pause
  * @param _name Nome que aparecerá no botão
  * @param _action Acão a ser realizada ao ser ativado
  * @param _color Cor normal do botão
  * @param _colorHighlighted Cor a exibir quando com o mouse por cima
  * @param _colorFont Cor normal da fonte do botão
  * @param _colorFontHighlighted Cor a exibir a fonte quando com o mouse por cima
  */
class PlayButton(
                  _name: String,
                  _action: (GameButton) => Unit,
                  _color: Color = new Color(0,0,0.5f,1),
                  _colorHighlighted: Color = new Color(0,0,0.9f,1),
                  _colorFont: Color = Color.WHITE,
                  _colorFontHighlighted: Color = Color.BLACK
                ) extends GameButton(
                  _name,
                  _action,
                  _color,
                  _colorHighlighted,
                  _colorFont,
                  _colorFontHighlighted
                ) with Image {

  var paused = false

  override def heightImage: Int = 12

  override def widthImage: Int = if(paused) 12 else 9

  def changeState(): Unit = paused = !paused

  override def drawName(font: BitmapFont, fontBatch: SpriteBatch): Unit = {} //desabilito desenhar a fonte

  override def drawInside(shapeRenderer: ShapeRenderer): Unit ={
    if(highlight) shapeRenderer.setColor(colorFontHighlighted)
    else shapeRenderer.setColor(colorFont)
    val padW = (width - widthImage)/2
    val padH = (height - heightImage)/2
    generateImage(shapeRenderer, pos1.x.toInt + padW, pos1.y.toInt + padH)
  }

  override def generateImage(shapeRenderer: ShapeRenderer, x: Int, y: Int): Unit = {

    if (paused) {
      val p1 = new Vector2(x, y)
      val p2 = new Vector2(x, y + 12)
      val p3 = new Vector2(x + 12, y + 6)

      shapeRenderer.triangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y)
    } else {
      shapeRenderer.rect(x, y, 3, 12)
      shapeRenderer.translate(6, 0, 0)
      shapeRenderer.rect(x, y, 3, 12)
      shapeRenderer.translate(-6, 0, 0)
    }

  }

  def padW:Int = (this.width - this.widthImage)/2
  def padH:Int = (this.height - this.heightImage)/2

}
