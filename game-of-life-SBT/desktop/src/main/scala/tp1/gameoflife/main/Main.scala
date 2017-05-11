package tp1.gameoflife.main

import com.badlogic.gdx.backends.lwjgl._
import tp1.gameoflife.controller.GameController
import tp1.gameoflife.gameengine.GameEngine

import scala.collection.mutable.MutableList

object Main{
  val classNames = scala.io.Source.fromFile("C:/Users/hugon/IdeaProjects/GoL_TP1/TP1-GoL/game-of-life-SBT/desktop/src/main/scala/tp1/gameoflife/main/Classes").getLines()
  var classes : MutableList[GameEngine] = _
  private val height : Int = 85
  private val width : Int = 160
  def main(args: Array[String]): Unit = {
    classes = loadRules(classNames)

    val cfg = new LwjglApplicationConfiguration
    cfg.title = "Game Of Life"
    cfg.height = 900
    cfg.width = 1600
    cfg.forceExit = false
    new LwjglApplication(GameController, cfg)
  }

  def loadRules (names: Iterator[String]) : MutableList[GameEngine] = {
    val list : MutableList[GameEngine] = new MutableList[GameEngine]()

    for(i <- names){
      val current_class = Class.forName(i).getConstructors()(0).newInstance(height.asInstanceOf[AnyRef],width.asInstanceOf[AnyRef])
      print(current_class)
      list += current_class.asInstanceOf[GameEngine]
    }
    list
  }

  def getRule(pos: Int) : GameEngine = {
    try{
      classes(pos)
    }
    catch {
      case e: Exception => println(e.getMessage);null
    }
  }
}
