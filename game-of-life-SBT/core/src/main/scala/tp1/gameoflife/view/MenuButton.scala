package tp1.gameoflife.view

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.controller.GameController
import tp1.gameoflife.main.Main

class MenuButton(
                  _name: String,
                  _action: (GameButton) => Unit =
                  b => {
                   GameController.pauseGame()
                    b match{case m: MenuButton => m.changeState()}
                  },
                  _color: Color = new Color(0,0,0.5f,1),
                  _colorHighlighted: Color = new Color(0,0,0.9f,1),
                  _colorFont: Color = Color.WHITE,
                  _colorFontHighlighted: Color = Color.BLACK,
                  _backgroundColor: Color = new Color(0.3f,0.3f,0.3f,0.5f)
                ) extends GameButton(
                  _name,
                  _action,
                  _color,
                  _colorHighlighted,
                  _colorFont,
                  _colorFontHighlighted
                ) with Menu {

  override val buttonW: Int = 100
  override val buttonH: Int = 30
  override val interButtonW: Int = 10
  override val interButtonH: Int = 10
  override val rows: Int = 6
  override val columns: Int = 4
  override val backgroundColor: Color = _backgroundColor
  override var buttons: List[GameButton] = createButtons();arrangeButtons()

  private def createButtons(): List[GameButton] =
    Main.classes.map(r => new GameButton(r.toString, _ => {
      GameController.changeRule(r)
      this.setName(GameController.getGameModeName)
    })).toList

}

