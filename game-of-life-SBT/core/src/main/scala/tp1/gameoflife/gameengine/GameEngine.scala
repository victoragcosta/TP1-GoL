package tp1.gameoflife.gameengine

import com.badlogic.gdx.graphics.Color

abstract class GameEngine {

  // Identificação dos modos

  def toString: String
  val description: String

  //==//

  // Valores das dimensões do tabuleiro.

  val height: Int
  val width: Int

  //==//

  // Valores do sistema de undo/redo e do número de gerações de afterLife.

  val mementoNumber: Int = 10
  val defaultAfterLifeCount: Int = 0

  //==//

  // Declaração e inicialização da geração atual.

  var currentGeneration = new Table(height, width)
  this.currentGeneration.create()

  //==//

  // Armazenamento de gerações para o sistema undo/redo.

  private var pastGenerations: List[Table] = List()
  private var redoGenerations: List[Table] = List()

  //==//

  // Definição de cores padrão.

  val darkGrey: Color = new Color(0.2f, 0.2f, 0.2f, 1)
  val grey: Color = new Color(0.5f, 0.5f, 0.5f, 1)
  val silver: Color = new Color(0.75f, 0.75f, 0.75f, 1)

  val defaultColor: Color = grey
  val defaultDeathColor: Color = darkGrey
  val defaultAfterLifeColor: Color = silver

  //==//

  // Métodos a serem sobrescritos pelas subclasses responsáveis pelas regras do modo de jogo.

  def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean
  def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean

  //==//

  // Métodos relacionados a cor. Podem ou não ser sobrescritos pelas subclasses.

  def determineCellColor(cellHeight: Int, cellWidth: Int): Color = this.defaultColor
  def switchColor (cellHeight: Int, cellWidth: Int): Unit = {}
  def updateColors (generation: Table): Unit = {}

  //==//

  // Método para passar o tabuleiro para a próxima geração.

  def nextGeneration(): Unit = {

    val newGeneration = new Table(height, width)
    newGeneration.create()

    for (h <- 0 until height) {
      for (w <- 0 until width) {

        if (!this.currentGeneration.elements(h)(w).alive) {

          newGeneration.elements(h)(w).alive = shouldRevive(h, w)

          if (shouldRevive(h, w)) {
            newGeneration.elements(h)(w).color = determineCellColor(h, w)
            Statistics.addRevive()
          }

          else {

            if (this.currentGeneration.elements(h)(w).afterLife) {

              val currentAfterLife = this.currentGeneration.elements(h)(w).afterLifeCount

              putInAfterLife(newGeneration, h, w, currentAfterLife)

            }

            else
              newGeneration.elements(h)(w).color = defaultDeathColor

          }

        }

        if (this.currentGeneration.elements(h)(w).alive) {

          newGeneration.elements(h)(w).alive = shouldKeepAlive(h, w)

          if (shouldKeepAlive(h, w))
            newGeneration.elements(h)(w).color = this.currentGeneration.elements(h)(w).color

          else {
            putInAfterLife(newGeneration, h, w)
            Statistics.addDeath()
          }

        }

      }
    }

    storeGeneration(this.currentGeneration)

    updateAfterLife(newGeneration)
    updateColors(newGeneration)

    this.currentGeneration = newGeneration

  }

  //==//

  // Métodos relacionados a mudar o estado de uma célula.

  def reviveCell(cellHeight: Int, cellWidth: Int): Unit = {

    this.currentGeneration.elements(adjustHeight(cellHeight))(adjustWidth(cellWidth)).alive = true
    this.currentGeneration.elements(adjustHeight(cellHeight))(adjustWidth(cellWidth)).color = defaultColor

  }

  def killCell(cellHeight: Int, cellWidth: Int): Unit = {

    this.currentGeneration.elements(adjustHeight(cellHeight))(adjustWidth(cellWidth)).alive = false
    this.currentGeneration.elements(adjustHeight(cellHeight))(adjustWidth(cellWidth)).color = defaultDeathColor

  }

  def putInAfterLife(generation: Table, cellHeight: Int, cellWidth: Int, count: Int = defaultAfterLifeCount): Unit = {

    generation.elements(cellHeight)(cellWidth).afterLife = true
    generation.elements(cellHeight)(cellWidth).afterLifeCount = count
    generation.elements(cellHeight)(cellWidth).color = defaultAfterLifeColor

  }

  //==//

  // Métodos relacionados a verificar se uma ou mais células estão vivas, bem como seu número.

  def isCellAlive(cellHeight: Int, cellWidth: Int): Boolean =
    this.currentGeneration.elements(adjustHeight(cellHeight))(adjustWidth(cellWidth)).alive

  def neighborsAlive(cellHeight: Int, cellWidth: Int): Int = {

    var aliveCount = 0

    for (i <- -1 to 1) {
      for (j <- -1 to 1) {

        if (this.currentGeneration.elements(adjustHeight(cellHeight + i))(adjustWidth(cellWidth + j)).alive)
          aliveCount += 1

      }
    }

    if (this.currentGeneration.elements(adjustHeight(cellHeight))(adjustWidth(cellWidth)).alive)
      aliveCount -= 1

    aliveCount

  }

  def cellsAlive(): Int = {

    var cellsAlive = 0

    for(h <- 0 until height) {
      for(w <- 0 until width) {

        if(this.currentGeneration.elements(h)(w).alive)
          cellsAlive += 1

      }
    }

    cellsAlive

  }

  //==//

  // Método para gerenciar as gerações restantes de uma célula em afterlife.

  def updateAfterLife (generation: Table): Unit = {

    for (h <- 0 until generation.getHeight) {
      for (w <- 0 until generation.getWidth) {

        if(generation.elements(h)(w).afterLife) {

          if (generation.elements(h)(w).afterLifeCount > 0)
            generation.elements(h)(w).afterLifeCount -= 1

          else {
            generation.elements(h)(w).afterLife = false
            generation.elements(h)(w).afterLifeCount = 0
            generation.elements(h)(w).color = defaultDeathColor
          }

        }

      }
    }

  }

  //==//

  // Métodos relacionados ao sistema de undo/redo.

  private def storeGeneration(generation: Table): Unit = {

    val length: Int = pastGenerations.length

    if (length >= mementoNumber)
      pastGenerations = pastGenerations.dropRight(length + 1 - mementoNumber)

    pastGenerations = generation :: pastGenerations

  }

  def undo(): Unit = {

    if (pastGenerations.isEmpty) {

      throw new RuntimeException

    }

    else {

      redoGenerations = this.currentGeneration :: redoGenerations
      this.currentGeneration = pastGenerations.head
      pastGenerations = pastGenerations.drop(1)

    }

  }

  def redo(): Unit = {

    if (redoGenerations.isEmpty) {

      throw new RuntimeException

    }

    else {

      pastGenerations = this.currentGeneration :: pastGenerations
      this.currentGeneration = redoGenerations.head
      redoGenerations = redoGenerations.drop(1)

    }

  }

  //==//

  // Método para resetar as cores do tabuleiro.

  def resetColors(generation: Table): Unit = {

    for(h <- 0 until height) {
      for(w <- 0 until width) {

        this.currentGeneration.elements(h)(w).color = defaultColor

      }
    }

  }

  //==//

  // Métodos para tornar o tabuleiro infinito.

  def adjustHeight (value: Int): Int = {

    var newValue = value

    while (newValue < 0)
      newValue += this.height

    while (newValue >= this.height)
      newValue -= this.height

    newValue

  }

  def adjustWidth (value: Int): Int = {

    var newValue = value

    while (newValue < 0)
      newValue += this.width

    while (newValue >= this.width)
      newValue -= this.width

    newValue

  }

  //==//

}
