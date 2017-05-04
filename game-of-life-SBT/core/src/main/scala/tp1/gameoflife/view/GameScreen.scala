package tp1.gameoflife.view

import com.badlogic.gdx.{Gdx, Screen}
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.Vector3

class GameScreen extends Screen{

  var width = Gdx.graphics.getWidth
  var height = Gdx.graphics.getHeight
  val camera = new OrthographicCamera(width, height)
  camera.position.set(width/2, height/2, 0)
  camera.update
  val batch = new SpriteBatch
  val shapeRenderer = new ShapeRenderer
  shapeRenderer.setProjectionMatrix(camera.combined)

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    Gdx.gl.glLineWidth(1.5f)

    batch.begin
    GameView.vivas.map(c => drawSquare(c, GameView.squareSide))
    batch.end
  }

  override def resume(): Unit = {}
  override def show(): Unit = {}
  override def pause(): Unit = {}
  override def hide(): Unit = {}
  override def resize(width: Int, height: Int): Unit = {}
  override def dispose(): Unit = {}

  private def drawSquare(v: Vector3, side: Float) = {
    shapeRenderer.begin(ShapeType.Filled)
    shapeRenderer.rect(v.x, v.y, side, side)
    shapeRenderer.end()
  }

}
