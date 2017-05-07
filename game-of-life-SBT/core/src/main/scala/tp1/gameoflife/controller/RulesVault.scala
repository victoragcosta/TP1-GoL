package tp1.gameoflife.controller

import tp1.gameoflife.defaultmodes.{Classic, HighLife}
import tp1.gameoflife.gameengine.GameEngine

object RulesVault {
  val height = 53
  val width = 80
  val list: List[GameRule] = new GameRule("Classic", new Classic(height,width))::
    new GameRule("HighLife", new HighLife(height,width))::Nil

  def find(name: String): Option[GameEngine] = {
    list.foreach(rule => if(rule.name.toLowerCase == name.toLowerCase()) return Some(rule.getRule))
    None
  }
}
