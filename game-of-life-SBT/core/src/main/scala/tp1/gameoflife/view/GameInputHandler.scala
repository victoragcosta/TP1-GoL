package tp1.gameoflife.view

import java.util.Calendar

import com.badlogic.gdx.Input.{Buttons, Keys}
import com.badlogic.gdx.{Gdx, InputProcessor}
import com.badlogic.gdx.math.Vector2
import tp1.gameoflife.controller.GameController

class GameInputHandler extends InputProcessor {
  private var lastClicked: Int = _
  private var lastCell: Vector2 = _
  private var lastTime: Long = Calendar.getInstance().getTimeInMillis
  private val clickDelay: Long = 500

  private val h = Gdx.graphics.getHeight

  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {false}

  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    val but = getButton(screenX, screenY)
    if(but != null)
      but.action()
    else if(!GameView.menuOpen)
      interactCell(screenX, screenY, button)
    lastClicked = button
    true
  }

  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = {
    touchDown(screenX, screenY, pointer, lastClicked)
    overButton(screenX, screenY)
    true
  }

  override def mouseMoved(screenX: Int, screenY: Int): Boolean = {
    overButton(screenX, screenY)
    true
  }

  override def scrolled(amount: Int): Boolean = {false}

  override def keyUp(keycode: Int): Boolean = {false}

  override def keyDown(keycode: Int): Boolean = {
    keycode match {
      case Keys.ESCAPE => GameController.endGame()
      case _ =>
    }
    true
  }

  override def keyTyped(character: Char): Boolean = {
    character match {
      case ' ' => GameController.changeAutoGenState()
      case 'b' => GameController.previousGeneration()
      case 'n' => GameController.nextGeneration()
      case 'c' => GameController.killAll()
      case 'r' => GameController.changeRule()
      case _ =>
    }
    true
  }



  private def interactCell(screenX: Int, screenY: Int, button: Int) = {
    val deslocX = GameView.paddingW
    val deslocY = GameView.paddingH + GameView.menuH
    val pos = calculateCell(screenX - deslocX, Gdx.graphics.getHeight - (screenY + deslocY))
    button match {
      case Buttons.LEFT =>
        val now = Calendar.getInstance().getTimeInMillis
        if((pos != lastCell || now >= lastTime + clickDelay) && GameController.cellIsAlive(pos.x.toInt, pos.y.toInt)){
          GameController.switchColor(pos.x.toInt, pos.y.toInt)
          lastTime = now
          lastCell = pos
        } else {
          GameController.makeAlive(pos.x.toInt, pos.y.toInt)
        }
      case Buttons.RIGHT => GameController.killCell(pos.x.toInt, pos.y.toInt)
      case _ =>
    }
  }

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

  private def inBounds(bt: GameButton, screenX: Int, screenY: Int) = {
    bt.pos1.x < screenX && screenX < bt.pos2.x && bt.pos1.y < h - screenY && h - screenY < bt.pos2.y
  }

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

  private def calculateCell(mouseX: Int, mouseY: Int): Vector2 ={
    val x = Math.floor(mouseX/GameView.squareSide)
    val y = Math.floor(mouseY/GameView.squareSide)
    new Vector2(x.toFloat,y.toFloat)
  }

}
