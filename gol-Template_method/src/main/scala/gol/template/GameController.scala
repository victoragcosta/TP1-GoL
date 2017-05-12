package gol.template

import scala.collection.mutable.MutableList

/**
 * Relaciona o componente View com o componente Model. 
 * 
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */

object GameController {

  private final val CLASSIC = 0
  private final val HIGHLIFE = 1

  private var allModes : MutableList[GameEngine] = new MutableList[GameEngine]
  loadRules(allModes)

  var currentMode : Int = CLASSIC

  def loadRules (all_modes: MutableList[GameEngine]) : Unit = {

    all_modes += new (Classic)
    all_modes += new (HighLife)

  }

  def getRules(i: Int) : GameEngine = {
    allModes(i)
  }

  def changeRules() : Unit = {
    if(currentMode != HIGHLIFE) {
      currentMode += 1
    }
    else
      currentMode = CLASSIC
    print("New Mode: "+getRules(currentMode).toString)
    GameView.update()
  }

  def start () : Unit = {
    GameView.update()
  }
  
  def halt() : Unit = {
    println("\n \n")
    Statistics.display
    System.exit(0)
  }

  def makeCellAlive(i: Int, j: Int) {
    try {
			getRules(currentMode).makeCellAlive(i, j)
			GameView.update()
		}
		catch {
		  case ex: IllegalArgumentException =>
		    println(ex.getMessage)
		}
  }
  
  def nextGeneration() : Unit = {
    getRules(currentMode).nextGeneration()
    GameView.update()
  }
  
}
