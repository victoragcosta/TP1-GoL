package tp1.gameoflife.gameengine

import com.badlogic.gdx.graphics.Color

abstract class GameEngine {

  def toString: String
  val description: String

  val height: Int
  val width: Int

  val mementoNumber: Int = 10
  val defaultAfterLifeCount: Int = 0

  var currentGeneration = new Table(height, width)
  this.currentGeneration.create()

  private var pastGenerations: List[Table] = List()
  private var redoGenerations: List[Table] = List()

  val darkGrey: Color = new Color(0.2f, 0.2f, 0.2f, 1)
  val grey: Color = new Color(0.5f, 0.5f, 0.5f, 1)
  val silver: Color = new Color(0.75f, 0.75f, 0.75f, 1)

  val defaultColor: Color = grey
  val defaultDeathColor: Color = darkGrey
  val defaultAfterLifeColor: Color = silver

  /**
    * Os métodos shouldRevive e shouldKeepAlive são abstratos e serão redefinidos pelas classes que implementam as regras de jogo
    * - shouldRevive determina se uma célula deve ser revivida
    * - shouldKeepAlive determina se uma célula deve sobreviver para a próxima geração
    * @param cellHeight : coordenada de altura da célula no tabuleiro
    * @param cellWidth : coordenada de largura da célula no tabuleiro
    * @return : Booleano, onde true é assertiva verdadeira aos nomes das funções
    */
  def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean
  def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean

  /**
    * Os métodos de cores determinam as cores das células e podem ser sobrescritos pelas classes de modos de jogo que mudam de cor
    * durante a sua execução
    * @param cellHeight : coordenada de altura da célula
    * @param cellWidth : coordenada de largura da célula
    * @return : objetos da classe Color
    */
  def determineCellColor(cellHeight: Int, cellWidth: Int): Color = this.defaultColor
  def switchColor (cellHeight: Int, cellWidth: Int): Unit = {}



  /**
    * Esse método coloca todas as células do tabuleiro na cor default. Usado nas transições de modos de jogo.
    * @param generation : Tabuleiro da geração atual
    */
  def resetColors(generation: Table): Unit = {

    for(h <- 0 until height) {
      for(w <- 0 until width) {

        this.currentGeneration.elements(h)(w).color = defaultColor

      }
    }

  }

  def updateColors (generation: Table): Unit = {}

  /**
    * As funções adjustHeight e adjustWidth ajustam a vizinhança na células da borda do tabuleiro, gerando o tabuleiro sem bordas
    * @param value : coordenada de altura ou largura da célula
    * @return : Inteiro com a nova coordenada
    */
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

  /**
    * Essa função realiza o somatório da quantidade das células vivas na geração atual
    * @return : Inteiro com o número de celulas vivas
    */
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

  /**
    * Dada um célula atráves de suas coordenadas, essa função computa a quantidade de vizinhos vivos em relação aquela célula
    * @param cellHeight : coordenada de altura da célula
    * @param cellWidth : coordenada de largura da célula
    * @return : Inteiro com a quantidade de vizinhos vivos
    */
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

  /**
    * Função retorna um booleano para o parametro alive de uma célula
    * @param cellHeight : coordenada de altura da celula
    * @param cellWidth : coordenada de largura da celula
    * @return : Booleano indicando o estado atual da célula
    */
  def isCellAlive(cellHeight: Int, cellWidth: Int): Boolean = this.currentGeneration.elements(adjustHeight(cellHeight))(adjustWidth(cellWidth)).alive

  /**
    * Os métodos killCell e aliveCell alteram o estado atual da célula apenas trocando o valor dos seus atributos
    * @param cellHeight : coordenada de altura da célula
    * @param cellWidth : coordenada de largura da célula
    */
  def killCell(cellHeight: Int, cellWidth: Int): Unit = {

    this.currentGeneration.elements(adjustHeight(cellHeight))(adjustWidth(cellWidth)).alive = false
    this.currentGeneration.elements(adjustHeight(cellHeight))(adjustWidth(cellWidth)).color = defaultDeathColor

  }

  def reviveCell(cellHeight: Int, cellWidth: Int): Unit = {

    this.currentGeneration.elements(adjustHeight(cellHeight))(adjustWidth(cellWidth)).alive = true
    this.currentGeneration.elements(adjustHeight(cellHeight))(adjustWidth(cellWidth)).color = defaultColor

  }

  /**
    * O método nextGeneration computa a próxima geração de células vivas, através da avaliação de dois estados.
    * 1. Para as células mortas, avalia-se o renascimento ou não para a proxima geração. Caso, ela permaneça morta, mantemos a cor no novo tabuleiro
    *    Se essa célula tiver morta e não for ser revivida, avalia-se a término ou não do estado de AfterLife (determinado pela classe com as regras).
    * 2. Para as células vivas, avalia-se ela deve ser mantida para a próxima geração, caso ela deva morrer, avalia-se a entrada no estado de AfterLife
    *
    * Após isso, a geração corrente é armazenada para a pilha de gerações (memento) e a geração é atualizada
    */
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

            if (this.currentGeneration.elements(h)(w).afterLife){
              newGeneration.elements(h)(w).afterLife = true
              newGeneration.elements(h)(w).afterLifeCount = this.currentGeneration.elements(h)(w).afterLifeCount
              newGeneration.elements(h)(w).color = defaultAfterLifeColor
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
            newGeneration.elements(h)(w).afterLife = true
            newGeneration.elements(h)(w).afterLifeCount += defaultAfterLifeCount
            newGeneration.elements(h)(w).color = defaultAfterLifeColor
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

  /**
    * As operações de undo e redo recuperam gerações da pilha (padrão memento) e colocam como a geração atual
    *
    * Ambas as funções possuem o
    * @throws : RuntimeException caso a pilha esteja vazia
    */
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

  /**
    * Guarda uma geração de células na pilha
    * @param generation : Tabuleiro da geração passada
    */
  private def storeGeneration(generation: Table): Unit = {

    val length: Int = pastGenerations.length

    if (length >= mementoNumber)
      pastGenerations = pastGenerations.dropRight(length + 1 - mementoNumber)

    pastGenerations = generation :: pastGenerations

  }

  /**
    * Essa função percorre o tabuleiro da geração atual e atualiza o estado de AfterLife para aquelas que possuem esse atributo true.
    * @param generation : Tabuleiro da geração atual
    */

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

}
