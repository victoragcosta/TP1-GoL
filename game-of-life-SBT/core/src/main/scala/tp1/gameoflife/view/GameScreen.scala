package tp1.gameoflife.view

import com.badlogic.gdx.{Gdx, Screen}
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

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
    Gdx.gl.glClearColor(1,1,1,0)
    Gdx.gl.glLineWidth(1.5f)

    batch.begin

    batch.end
  }

  override def resume(): Unit = {}
  override def show(): Unit = {}
  override def pause(): Unit = {}
  override def hide(): Unit = {}
  override def resize(width: Int, height: Int): Unit = {}
  override def dispose(): Unit = {}
}
