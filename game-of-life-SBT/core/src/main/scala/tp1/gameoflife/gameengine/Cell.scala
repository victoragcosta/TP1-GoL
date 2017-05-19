package tp1.gameoflife.gameengine

import com.badlogic.gdx.graphics.Color

class Cell (var alive: Boolean, var color: Color) {

  // Célula

  /**
    * A célula é a unidade padrão de um jogo da vida de Conway.
    * Para criar uma célula, é necessário dizer se ela está viva ou não e dizer sua cor.
    *
    * Possui 4 atributos:
    *
    * alive: Indica se a célula está viva ou não.
    * color: Indica a cor da célula.
    *
    * afterLife: Indica se a célula está em afterLife ou não.
    *
    * (OBS: Uma célula em afterlife está em um estado especial de decaimento,
    * onde ela não está morta nem viva. Mas, seu atributo alive é igual a falso
    * quando uma célula está em afterLife.)
    *
    * afterLifeCount: Indica por quantas gerações a mais a célula ficará no estado de afterlife.
    */

  var afterLife: Boolean = false
  var afterLifeCount: Int = 0

  //==//

}