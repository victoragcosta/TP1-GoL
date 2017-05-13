package tp1.gameoflife.main

import java.io.InputStream

import com.badlogic.gdx.backends.lwjgl._
import tp1.gameoflife.controller.GameController
import tp1.gameoflife.gameengine.GameEngine
import scala.collection.mutable.MutableList

object Main{
  val stream : InputStream = getClass.getResourceAsStream("/Classes.txt")
  val classNames = scala.io.Source.fromInputStream( stream ).getLines
  var classes : MutableList[GameEngine] = _
  private val height : Int = 53
  private val width : Int = 80
  def main(args: Array[String]): Unit = {
    classes = loadRules(classNames)

    val cfg = new LwjglApplicationConfiguration
    cfg.title = "Game Of Life"
    cfg.height = 580
    cfg.width = 800
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