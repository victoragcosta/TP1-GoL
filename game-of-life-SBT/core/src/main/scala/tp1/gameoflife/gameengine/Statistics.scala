package tp1.gameoflife.gameengine

object Statistics {

  private var numberOfRevives = 0
  private var numberOfDeaths = 0

  def showRevives(): Int = numberOfRevives

  def addRevive(): Unit = numberOfRevives += 1

  def showDeaths(): Int = numberOfDeaths

  def addDeath(): Unit = numberOfDeaths += 1

  def displayStatistics(): Unit = {
    println("                                     ")
    println("-------------------------------------")
    println("             Statistics              ")
    println("-------------------------------------")
    println("Number of revives: " + numberOfRevives)
    println("Number of deaths: " + numberOfDeaths)
    println("-------------------------------------")
    println("                                     ")
  }

}
