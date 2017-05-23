package tp1.gameoflife.controller

import java.util.Calendar

import com.badlogic.gdx.Input.{Buttons, Keys}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.{Gdx, InputProcessor}
import tp1.gameoflife.view.{GameButton, GameView, Menu}

class GameInputHandler extends InputProcessor {
  private var lastClicked: Int = _
  private var lastCell: Vector2 = _
  private var lastTime: Long = Calendar.getInstance().getTimeInMillis
  private val clickDelay: Long = 500

  private val h = Gdx.graphics.getHeight


  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {false}

  /**
    * Trata o clique na tela, tanto no tabuleiro quanto nos botões
    * @param screenX posição x na tela da esquerda pra direita
    * @param screenY posição y na tela do topo para baixo
    * @param pointer indice do ponteiro (multitouch)
    * @param button valor do botão apertado
    * @return se foi processado ou não
    */
  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    val but = getButton(screenX, screenY)
    if(but != null)
      but.action()
    else if(!GameView.menuOpen)
      interactCell(screenX, screenY, button)
    lastClicked = button
    true
  }

  /**
    * Trata a entrada de mouse quando arrasta, em geral só gera vários cliques
    * @param screenX posição x na tela da esquerda pra direita
    * @param screenY posição y na tela do topo para baixo
    * @param pointer indice do ponteiro (multitouch)
    * @return se foi processado ou não
    */
  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = {
    val deslocX = GameView.paddingW
    val deslocY = GameView.paddingH + GameView.menuH
    val pos = calculateCell(screenX - deslocX, Gdx.graphics.getHeight - (screenY + deslocY))
    if(pos != lastCell){
      interactCell(screenX, screenY, lastClicked)
      lastCell = pos
    }
    overButton(screenX, screenY)
    true
  }

  /**
    * Trata o movimento do mouse para dar highlight em botões
    * @param screenX posição x na tela da esquerda pra direita
    * @param screenY posição y na tela do topo para baixo
    * @return se foi processado ou não
    */
  override def mouseMoved(screenX: Int, screenY: Int): Boolean = {
    overButton(screenX, screenY)
    true
  }

  override def scrolled(amount: Int): Boolean = {false}

  override def keyUp(keycode: Int): Boolean = {false}

  /**
    * Trata o clique de teclas no teclado
    * @param keycode código da tecla pressionada
    * @return se foi processado ou não
    */
  override def keyDown(keycode: Int): Boolean = {
    keycode match {
      case Keys.ESCAPE => GameController.endGame()
      case _ =>
    }
    true
  }

  /**
    * Trata o clique de teclas no teclado
    * @param character caracter pressionado
    * @return se foi processado ou não
    */
  override def keyTyped(character: Char): Boolean = {
    character match {
      case ' ' => GameController.changeAutoGenState()
      case 'b' => GameController.previousGeneration()
      case 'n' => GameController.nextGeneration()
      case 'm' => GameController.redoGeneration()
      case '+' => GameController.speedUp(10)
      case '0' => GameController.speedReset()
      case '-' => GameController.speedDown(10)
      case 'c' => GameController.killAll()
      case 'r' => GameController.changeRule()
      case _ =>
    }
    true
  }


  /**
    * Calcula e decide o que fazer ao clicar no tabuleiro
    * @param screenX Posição horizontal a partir da esquerda
    * @param screenY Posição vertical a partir do topo
    * @param button código do botão pressionado
    */
  private def interactCell(screenX: Int, screenY: Int, button: Int): Unit = {
    val deslocX = GameView.paddingW
    val deslocY = GameView.paddingH + GameView.menuH
    val pos = calculateCell(screenX - deslocX, Gdx.graphics.getHeight - (screenY + deslocY))
    button match {
      case Buttons.LEFT =>
        if(GameController.cellIsAlive(pos.x.toInt, pos.y.toInt)){
          GameController.switchColor(pos.x.toInt, pos.y.toInt)
        } else {
          GameController.makeAlive(pos.x.toInt, pos.y.toInt)
        }
      case Buttons.RIGHT => GameController.killCell(pos.x.toInt, pos.y.toInt)
      case _ =>
    }
  }

  /**
    * Calcula o botão onde o cursor está
    * @param screenX Posição horizontal a partir da esquerda
    * @param screenY Posição vertical a partir do topo
    * @return Botão pressionado
    */
  private def getButton(screenX: Int, screenY: Int): GameButton = {
    val buttons = GameView.buttons
    buttons.foreach(b => {
      if (inBounds(b, screenX, screenY)) return b

      b match{
        case m: Menu =>
          if(m.activated)
            m.buttons.foreach(bt => if (inBounds(bt, screenX, screenY)) return bt)
        case _ =>
      }
    })
    null
  }

  /**
    * Calcula se está dentro do botão
    * @param bt Botão
    * @param screenX Posição horizontal a partir da esquerda
    * @param screenY Posição vertical a partir do topo
    * @return Se está sobre (true) ou não o Botão
    */
  private def inBounds(bt: GameButton, screenX: Int, screenY: Int):Boolean = {
    bt.pos1.x < screenX && screenX < bt.pos2.x && bt.pos1.y < h - screenY && h - screenY < bt.pos2.y
  }

  /**
    * Decide sobre o highlight dos botões
    * @param screenX Posição horizontal a partir da esquerda
    * @param screenY Posição vertical a partir do topo
    */
  private def overButton(screenX: Int, screenY: Int) = {
    val buttons = GameView.buttons
    buttons.foreach(b => {
      if (inBounds(b, screenX, screenY) && !GameView.menuOpen)
        b.setHighlight(true)
      else
        b.setHighlight(false)

      b match {
        case m: Menu =>
          if(m.activated)
            m.setHighlight(true)
          m.buttons.foreach(bt => {
            if (inBounds(bt, screenX, screenY))
              bt.setHighlight(true)
            else
              bt.setHighlight(false)
          })
        case _ =>
      }
    })
  }

  /**
    * Calcula as coordenadas da célula segundo o tabuleiro virtual
    * @param mouseX Posição horizontal na tela da esquerda para direita
    * @param mouseY Posição veritical na tela de baixo para cima
    * @return Posição no tabuleiro virtual
    */
  private def calculateCell(mouseX: Int, mouseY: Int): Vector2 ={
    val x = Math.floor(mouseX/GameView.squareSide)
    val y = Math.floor(mouseY/GameView.squareSide)
    new Vector2(x.toFloat,y.toFloat)
  }

}
