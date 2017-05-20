package tp1.gameoflife.view

import java.util.Calendar
import javafx.scene.PerspectiveCamera

import com.badlogic.gdx.{Gdx, Screen}
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.Vector2
import tp1.gameoflife.controller.GameController

class GameScreen extends Screen {

  //Largura e Altura da Tela em pixels respectivamente
  private val width: Int = Gdx.graphics.getWidth
  private val height: Int = Gdx.graphics.getHeight

  //Cria as classes necessárias para gerar os frames
  private val camera = new OrthographicCamera(width, height)  //Câmera ortográfica, escolhe para onde olhamos
  camera.position.set(width/2, height/2, 0)  //Ajusta para o centro da tela
  camera.update()  //Atualiza as configurações da camera
  private val batch = new SpriteBatch  //Esse batch que mantém o que é desenhado na tela
  private val shapeRenderer = new ShapeRenderer  //Gera formas, em geral para retângulos
  shapeRenderer.setProjectionMatrix(camera.combined)  //ajusta a matriz de projeção do shape renderer
  private val fontBatch = new SpriteBatch  //Esse batch mantém o texto a ser escrito na tela
  private val font = new BitmapFont()  //Fonte padrão

  /**
    * Desenha o fundo do tabuleiro centralizadamente, gerando assim o menu.
    */
  private def drawMenu(): Unit ={
    shapeRenderer.begin(ShapeType.Filled)

    shapeRenderer.setColor(0.9f,0.9f,0.9f,1)
    val start = new Vector2(GameView.paddingW, GameView.menuH + GameView.paddingH)
    val sides = new Vector2(width - 2*GameView.paddingW, height - 2*GameView.paddingH - GameView.menuH)
    shapeRenderer.rect(start.x, start.y, sides.x, sides.y)

    shapeRenderer.end()
  }

  /**
    * Desenha uma célula do tabuleiro
    * @param c Célula viva a ser desenhada, contém a Célula e a posição dela.
    * @param side Lado do quadrado.
    */
  private def drawSquare(c: LiveCell, side: Float) = {
    shapeRenderer.begin(ShapeType.Filled)
    //shapeRenderer.setColor(0.5f,0.5f,0.5f,1)
    shapeRenderer.setColor(c.color)
    shapeRenderer.rect(c.x, c.y, side, side)
    shapeRenderer.end()
  }

  /**
    * Pega as células da GameView e manda desenhá-las
    */
  private def drawAliveCells(): Unit ={
    shapeRenderer. translate(GameView.paddingW, GameView.paddingH + GameView.menuH, 0)
    if(GameView.vivas != null)
      GameView.vivas.foreach(c => drawSquare(c, GameView.squareSide))
    shapeRenderer. translate(-GameView.paddingW, -GameView.paddingH - GameView.menuH, 0)
  }

  /**
    * Desenha todos os botões do menu e de qualquer menu pop up
    */
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

  /**
    * Gera o texto dos botões
    * (aparentemente se eu gerar junto de imagem acontece algum bug e não mostra o texto)
    */
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

  //Guarda o tempo da última troca de geração
  private var lastChange = Calendar.getInstance().getTimeInMillis
  /**
    * Passa de Geração automáticamente
    */
  private def autoChangeFrame(): Unit = {
    if (!GameView.paused && Calendar.getInstance().getTimeInMillis >= lastChange + GameView.delay) {
      GameController.nextGeneration()
      lastChange = Calendar.getInstance().getTimeInMillis
    }
  }

  /**
    * Chamado uma vez por frame para gerar a tela.
    * @param delta Tempo para processar o último frame.
    */
  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0.2f,0.2f,0.2f,1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    Gdx.gl.glLineWidth(1.5f)

    //Imagens
    batch.begin()
    drawMenu()
    drawAliveCells()
    renderButtons()
    batch.end()

    //Texto
    fontBatch.begin()
    renderButtonsNames()
    fontBatch.end()

    //Mudar a geração
    autoChangeFrame()
  }

  override def resume(): Unit = {}
  override def show(): Unit = {}
  override def pause(): Unit = {}
  override def hide(): Unit = {}
  override def resize(width: Int, height: Int): Unit = {}
  //Chamado ao terminar com a tela
  override def dispose(): Unit = {
    batch.dispose()
    fontBatch.dispose()
    font.dispose()
    shapeRenderer.dispose()
  }

}
