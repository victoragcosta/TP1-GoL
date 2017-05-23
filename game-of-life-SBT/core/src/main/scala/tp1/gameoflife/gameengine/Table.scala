package tp1.gameoflife.gameengine

import com.badlogic.gdx.graphics.Color

class Table (height: Int, width: Int) {

  // Variável que armazena as células de uma geração.

  /**
    * Está variável é uma matriz de células de dimensão height x width.
    * Ela representa um tabuleiro contendo uma geração de células.
    */

  var elements: Array[Array[Cell]] = Array.ofDim[Cell](height, width)

  //==//

  // Cor padrão das células de uma geração.

  /**
    * Ao se inicializar uma célula, esta é a cor que será utilizada.
    */

  val darkGrey: Color = new Color(0.2f, 0.2f, 0.2f, 1)

  //==//

  // Métodos para retornar as dimensões do tabuleiro que contém a geração.

  /**
    * Obter altura
    * Retorna a altura do tabuleiro.
    */

  def getHeight: Int = this.height


  /**
    * Obter largura
    * Retorna a largura do tabuleiro.
    */

  def getWidth: Int = this.width

  //==//

  // Método para inicializar um geração.

  /**
    * Criar tabuleiro
    * Inicializa todas as células do tabuleiro como células mortas cuja cor é a cor padrão.
    */

  def create(): Unit = {

    for (h <- 0 until height) {
      for (w <- 0 until width) {
        this.elements(h)(w) = new Cell(false, darkGrey)
      }
    }

  }

  //==//

  // Método para limpar um tabuleiro, retornando ao estado de inicialização.

  /**
    * Limpar tabuleiro
    * Mata e muda a cor de todas as células do tabuleiro.
    * Isso efetivamente retorna o tabuleiro ao seu estado de inicialização.
    */

  def clean(): Unit = {

    for (column <- this.elements) {
      for (cell <- column) {
        cell.alive = false
        cell.afterLife = false
        cell.afterLifeCount = 0
        cell.color = darkGrey
      }
    }

  }

  //==//

  def foreach(f:(Cell) => (Unit)): Unit ={
    elements.foreach(a => a.foreach(f))
  }

}
