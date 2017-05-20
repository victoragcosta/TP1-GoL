package tp1.gameoflife.view

import java.util.Calendar

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

/**
  * Gera um botão capaz de acionar uma ação ao ser tocado
  * @param _name Nome que aparecerá no botão
  * @param _action Acão a ser realizada ao ser ativado
  * @param _color Cor normal do botão
  * @param _colorHighlighted Cor a exibir quando com o mouse por cima
  * @param _colorFont Cor normal da fonte do botão
  * @param _colorFontHighlighted Cor a exibir a fonte quando com o mouse por cima
  */
class GameButton(
                  var _name: String,
                  _action: (GameButton) => Unit,
                  var _color: Color = new Color(0,0,0.5f,1),
                  var _colorHighlighted: Color = new Color(0,0,0.9f,1),
                  _colorFont: Color = Color.WHITE,
                  _colorFontHighlighted: Color = Color.BLACK
                ){

  //Getters
  def name: String = _name
  def setName(namey: String): Unit = _name = namey
  def color: Color = _color
  def colorHighlighted: Color = _colorHighlighted
  def colorFont: Color = _colorFont
  def colorFontHighlighted: Color = _colorFontHighlighted

  //Canto esquerdo inferior
  var pos1: Vector2 = _
  //Canto superior direito
  var pos2: Vector2 = _

  /**
    * Muda a posição do canto inferior esquerdo
    * @param x X do ponto
    * @param y Y do ponto
    */
  def setPosition1(x: Int, y: Int): Unit ={
    pos1 = new Vector2(x,y)
  }

  /**
    * Muda a posição do canto superior direito
    * @param x X do ponto
    * @param y Y do ponto
    */
  def setPosition2(x: Int, y: Int): Unit ={
    pos2 = new Vector2(x,y)
  }

  /**
    * Retorna a largura do botão
    * @return largura do botão
    */
  def width: Int = (pos2.x - pos1.x).toInt

  /**
    * Retorna a altura do botão
    * @return altura do botão
    */
  def height: Int = (pos2.y - pos1.y).toInt

  //Mouse em cima ou não
  var highlight: Boolean = false

  /**
    * Muda o estado para mouse em cima ou não
    * @param state Boolean estado de mouse em cima(true) ou não(false)
    */
  def setHighlight(state: Boolean): Unit ={
    highlight = state
  }

  /**
    * Altera a cor do botão normal e de highlight
    * @param color Color cor normal
    */
  def setColor(color: Color): Unit ={
    this._color = color
    this._colorHighlighted = new Color(color.r+0.4f, color.g+0.4f, color.b+0.4f, 1)
  }

  //Controle de velocidade de clique do botão
  private var debounce = false
  private var lastTime = Calendar.getInstance().getTimeInMillis
  private val delay = 50

  /**
    * Executa o clique do botão
    */
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

  /**
    * Calcula o padding para centralizar a fonte
    * @param font fonte do texto
    * @return Padding horizontal
    */
  def padW(font: BitmapFont): Int = ((this.width - font.getBounds(this.name).width)/2).toInt
  /**
    * Calcula o padding para centralizar a fonte
    * @param font fonte do texto
    * @return Padding vertical
    */
  def padH(font: BitmapFont): Int = ((this.height - font.getBounds(this.name).height)/2).toInt

  /**
    * Desenha o nome do botão na tela
    * @param font fonte do texto
    * @param fontBatch batch em que vai ser escrito
    */
  def drawName(font: BitmapFont, fontBatch: SpriteBatch): Unit ={
    if(highlight){
      font.setColor(colorFontHighlighted)
    } else {
      font.setColor(colorFont)
    }
    font.draw(fontBatch, name, pos1.x + padW(font), pos2.y - padH(font))
  }

  /**
    * Desenha o botão
    * @param shapeRenderer ShapeRenderer para desenhar
    */
  def drawButton(shapeRenderer: ShapeRenderer): Unit ={
    if(highlight) shapeRenderer.setColor(colorHighlighted)
    else shapeRenderer.setColor(color)
    shapeRenderer.rect(pos1.x, pos1.y, width, height)
  }

}
