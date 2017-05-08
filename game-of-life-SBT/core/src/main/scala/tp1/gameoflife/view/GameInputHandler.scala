package tp1.gameoflife.view

import com.badlogic.gdx.Input.{Buttons, Keys}
import com.badlogic.gdx.{Gdx, InputProcessor}
import com.badlogic.gdx.math.Vector2
import tp1.gameoflife.controller.GameController

class GameInputHandler extends InputProcessor {
  private var lastClicked: Int = _

  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {false}

  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    interactCell(screenX, screenY, button)
    val but = getButtonNumber(screenX, screenY)
    if(but != -1)
      GameView.buttons(but).action()
    true
  }

  override def keyUp(keycode: Int): Boolean = {false}

  override def scrolled(amount: Int): Boolean = {false}

  override def keyTyped(character: Char): Boolean = {
    character match {
      case ' ' => GameController.changeState()
      case 'b' => GameController.previousGeneration()
      case 'n' => GameController.nextGeneration()
      case 'c' => GameController.killAll()
      case 'r' => GameController.changeRule()
      case _ =>
    }
    true
  }

  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = {
    touchDown(screenX, screenY, pointer, lastClicked)
  }

  override def keyDown(keycode: Int): Boolean = {
    keycode match {
      case Keys.ESCAPE => GameController.endGame()
      case _ =>
    }
    true
  }

  override def mouseMoved(screenX: Int, screenY: Int): Boolean = {
    overButton(screenX, screenY)
    true
  }



  private def interactCell(screenX: Int, screenY: Int, button: Int) = {
    val deslocX = GameView.paddingW
    val deslocY = GameView.paddingH + GameView.menuH
    val pos = calculateCell(screenX - deslocX, Gdx.graphics.getHeight - (screenY + deslocY))
    button match {
      case Buttons.LEFT => GameController.makeAlive(pos.x.toInt, pos.y.toInt)
      case Buttons.RIGHT => GameController.killCell(pos.x.toInt, pos.y.toInt)
      case _ =>
    }
    lastClicked = button
  }

  private def getButtonNumber(screenX: Int, screenY: Int): Int = {
    val h = Gdx.graphics.getHeight
    val buttons = GameView.buttons
    for(i <- buttons.indices){
      val b = buttons(i)
      if (b.pos.x < screenX && screenX < b.pos.x + GameView.buttonW &&
        b.pos.y < h - screenY && h - screenY < b.pos.y + GameView.buttonH) {
        return i
      }
    }
    -1
  }

  private def overButton(screenX: Int, screenY: Int) = {
    val h = Gdx.graphics.getHeight
    val buttons = GameView.buttons
    buttons.foreach(b => {
      if (b.pos.x < screenX && screenX < b.pos.x + GameView.buttonW &&
        b.pos.y < h - screenY && h - screenY < b.pos.y + GameView.buttonH) {
        b.setHighlight(true)
      } else {
        b.setHighlight(false)
      }
    })
  }

  private def calculateCell(mouseX: Int, mouseY: Int): Vector2 ={
    val x = Math.floor(mouseX/GameView.squareSide)
    val y = Math.floor(mouseY/GameView.squareSide)
    new Vector2(x.toFloat,y.toFloat)
  }
}
