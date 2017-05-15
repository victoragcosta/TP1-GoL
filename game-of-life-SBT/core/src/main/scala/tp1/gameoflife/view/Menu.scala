package tp1.gameoflife.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

import scala.io.StdIn

trait Menu {

  var activated: Boolean = false

  var pos1Menu: Vector2 = new Vector2(0,GameView.menuH)
  var pos2Menu: Vector2 = new Vector2(Gdx.graphics.getWidth, Gdx.graphics.getHeight)

  val buttonW: Int
  val buttonH: Int
  val interButtonW: Int
  val interButtonH: Int
  var buttons: List[GameButton]
  val rows: Int
  val columns: Int

  val backgroundColor: Color

  def setBLCorner(x: Int, y: Int): Unit = pos1Menu = new Vector2(x, y)
  def setTRCorner(x: Int, y: Int): Unit = pos2Menu = new Vector2(x, y)

  def menuW: Int = (pos2Menu.x - pos1Menu.x).toInt
  def menuH: Int = (pos2Menu.y - pos1Menu.y).toInt

  def drawBackground(shapeRenderer: ShapeRenderer): Unit ={
    shapeRenderer.setColor(backgroundColor)
    shapeRenderer.rect(pos1Menu.x, pos1Menu.y, menuW, menuH)
    val b = backgroundColor
    val secondaryColor = new Color(b.r-0.4f,b.g-0.4f,b.b-0.4f,1)
    shapeRenderer.setColor(secondaryColor)
    shapeRenderer.rect(pos1Menu.x, pos1Menu.y, 10, menuH)
    shapeRenderer.rect(pos1Menu.x, pos1Menu.y, menuW, 10)
    shapeRenderer.rect(pos2Menu.x-10, pos1Menu.y, 10, menuH)
    shapeRenderer.rect(pos1Menu.x, pos2Menu.y-10, menuW, 10)

  }

  def drawMenu(shapeRenderer: ShapeRenderer): Unit = {
    if(activated){
      drawBackground(shapeRenderer)
      buttons.foreach(b => b.drawButton(shapeRenderer))
    }
  }

  def arrangeButtons(): Unit ={
    val buttonPadSides = (menuW - (columns*buttonW + (columns-1)*interButtonW))/2
    val buttonPadTopBot = (menuH - (rows*buttonH + (rows-1)*interButtonH))/2

    var posx: Int = buttonPadSides
    var posy: Int = Gdx.graphics.getHeight - buttonPadTopBot
    var column: Int = 1

    for(i <- buttons.indices){
      val gameButton = buttons(i)

      gameButton.setPosition1(posx, posy)
      gameButton.setPosition2(posx + buttonW, posy + buttonH)

      if(i >= rows*column-1) {
        column += 1
        posx += (buttonW + interButtonW)
        posy = Gdx.graphics.getHeight - buttonPadTopBot
      } else {
        posy -= (buttonH + interButtonH)
      }

    }
  }

  def changeState(): Unit = {
    activated = !activated
    GameView.lockedTable = activated
  }

}
