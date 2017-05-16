package tp1.gameoflife.controller

import java.io.InputStream

import tp1.gameoflife.gameengine.GameEngine

import scala.collection.mutable.MutableList

object DependencyInjector {

  val stream : InputStream = getClass.getResourceAsStream("/Classes.txt")
  private val classNames = scala.io.Source.fromInputStream( stream ).getLines
  private val height : Int = 60
  private val width : Int = 100

  def loadRules (names: Iterator[String]) : MutableList[GameEngine] = {
    val list : MutableList[GameEngine] = new MutableList[GameEngine]()

    for(i <- names){
      val current_class = Class.forName(i).getConstructors()(0).newInstance(height.asInstanceOf[AnyRef],width.asInstanceOf[AnyRef])
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

  var classes : MutableList[GameEngine] = loadRules(DependencyInjector.classNames)

}
