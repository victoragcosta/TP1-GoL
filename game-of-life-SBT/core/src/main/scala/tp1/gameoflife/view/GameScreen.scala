package tp1.gameoflife.view

import java.util.Calendar

import com.badlogic.gdx.{Gdx, Screen}
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.{Vector2}
import tp1.gameoflife.controller.GameController

class GameScreen extends Screen {

  private val width: Int = Gdx.graphics.getWidth
  private val height: Int = Gdx.graphics.getHeight

  private val camera = new OrthographicCamera(width, height)
  camera.position.set(width/2, height/2, 10)
  camera.update()
  private val batch = new SpriteBatch
  private val shapeRenderer = new ShapeRenderer
  shapeRenderer.setProjectionMatrix(camera.combined)
  private val fontBatch = new SpriteBatch
  private val font = new BitmapFont()

  private def drawMenu(): Unit ={
    shapeRenderer.begin(ShapeType.Filled)

    shapeRenderer.setColor(0.9f,0.9f,0.9f,1)
    val start = new Vector2(GameView.paddingW, GameView.menuH + GameView.paddingH)
    val sides = new Vector2(width - 2*GameView.paddingW, height - 2*GameView.paddingH - GameView.menuH)
    shapeRenderer.rect(start.x, start.y, sides.x, sides.y)

    shapeRenderer.end()
  }

  private def drawSquare(c: LiveCell, side: Float) = {
    shapeRenderer.begin(ShapeType.Filled)
    //shapeRenderer.setColor(0.5f,0.5f,0.5f,1)
    shapeRenderer.setColor(c.color)
    shapeRenderer.rect(c.x, c.y, side, side)
    shapeRenderer.end()
  }

  private def drawAliveCells(): Unit ={
    shapeRenderer. translate(GameView.paddingW, GameView.paddingH + GameView.menuH, 0)
    if(GameView.vivas != null)
      GameView.vivas.foreach(c => drawSquare(c, GameView.squareSide))
    shapeRenderer. translate(-GameView.paddingW, -GameView.paddingH - GameView.menuH, 0)
  }

  private def renderButtons(): Unit ={
    val buttons = GameView.buttons
    if(buttons != null){
      shapeRenderer.begin(ShapeType.Filled)
      buttons.foreach(b => {
        b.drawButton(shapeRenderer)
        b match {
          case p: Image =>
            p.drawInside(shapeRenderer)
          case m: Menu =>
            m.drawMenu(shapeRenderer)
          case _ =>
        }
      })
      shapeRenderer.end()
    }
  }

  private def renderButtonsNames(): Unit ={
    val buttons = GameView.buttons
    if(buttons != null){
      buttons.foreach(b => {
        b.drawName(font, fontBatch)
        b match {
          case m: Menu =>
            if(m.activated)
              m.buttons.foreach(b => b.drawName(font, fontBatch))
          case _ =>
        }
      })
    }
  }

  private var lastChange = Calendar.getInstance().getTimeInMillis
  private def autoChangeFrame(): Unit = {
    if (!GameView.paused && Calendar.getInstance().getTimeInMillis >= lastChange + GameView.delay) {
      GameController.nextGeneration()
      lastChange = Calendar.getInstance().getTimeInMillis
    }
  }

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0.2f,0.2f,0.2f,1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    Gdx.gl.glLineWidth(1.5f)

    batch.begin()
    drawMenu()
    drawAliveCells()
    renderButtons()
    batch.end()

    fontBatch.begin()
    renderButtonsNames()
    fontBatch.end()

    autoChangeFrame()
  }

  override def resume(): Unit = {}
  override def show(): Unit = {}
  override def pause(): Unit = {}
  override def hide(): Unit = {}
  override def resize(width: Int, height: Int): Unit = {}
  override def dispose(): Unit = {
    batch.dispose()
    fontBatch.dispose()
    font.dispose()
    shapeRenderer.dispose()
  }

}
