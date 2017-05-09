package tp1.gameoflife.controller

import tp1.gameoflife.defaultmodes.{Classic, HighLife, ImmigrantLife, Maze}
import tp1.gameoflife.gameengine.GameEngine

object RulesVault {
  val height = 53
  val width = 80
  val list: List[GameEngine] = new Classic(height,width)::
    new HighLife(height,width)::
    new Maze(height,width)::
    new ImmigrantLife(height,width)::Nil

  def find(name: String): Option[GameEngine] = {
    list.foreach(rule => if(rule.toString.toLowerCase == name.toLowerCase()) return Some(rule))
    None
  }
}
