package tp1.gameoflife.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.{Vector2, Vector3}
import tp1.gameoflife.controller.GameController
import tp1.gameoflife.gameengine.{Cell, GameEngine, Table}

object GameView{

  //Auxiliar para pegar as células vivas em GameEngine
  var vivas: List[LiveCell] = _

  //Mantém a referência da tela
  val screen = new GameScreen

  //Controla para mudança de dimensões de tabuleiro
  var oldW: Int = 0
  var oldH: Int = 0

  //Variáveis relacionadas a centralizar a tela e tamanho das células
  final val minSquareSide: Float = 4  //Lado mínimo do quadrado
  var squareSide: Float = 10  //lado atual do quadrado
  var scrW: Int = Gdx.graphics.getWidth  //largura da tela
  var scrH: Int = Gdx.graphics.getHeight  //altura da tela
  val menuH: Int = 50  //Altura do menu abaixo do tabuleiro
  val menuW: Int = Gdx.graphics.getWidth  //Largura do menu abaixo do tabuleiro
  var paddingW: Int = 0  //padding lateral para manter tabuleiro central
  var paddingH: Int = 0  //padding vertical para manter o tabuleiro central

  //Variáveis relacionadas a botões
  private val buttonH: Int = 30  //Altura dos botões
  private val buttonW: Int = 100  //Largura dos botões
  private val buttonPad: Int = 10  //espaço lateral entre os botões
  val speedDisplay = new GameButton("Speed: 1.00", b => {
    GameController.speedReset()
  })
  val buttons: List[GameButton] = createButtons;arrangeButtons()  //Lista contendo todos os botões

  //Variáveis relacionadas ao fluxo das gerações automático
  var paused = false  //Controla se deve avançar automático
  val defaultDelay = 100  //Espera padrão antes de mudar de geração
  var delay: Int = defaultDelay  //Tempo de espera atual antes de mudar de geração

  //Cuida do estado do menu
  var menuOpen = false  //quando verdadeiro, bloqueia os outros botões

  //Geração de Valores para uso da Tela
  /**
    * Contém os procedimentos necessários para atualizar as células.
    * Atualiza as Células vivas e caso necessário o padding também.
    * @param cells Conjunto de células vivas do tipo Table.
    * @param w Largura do tabuleiro.
    * @param h Altura do tabuleiro.
    */
  def update(cells: Table, w: Int, h: Int): Unit = {
    if(w != oldW){
      oldW = w
      calculatePadding(w,h)
    }
    if(h != oldH){
      oldH = h
      calculatePadding(w, h)
    }
    updateLiveCells(cells, w, h)
  }

  /**
    * Atualiza as Células vivas na View.
    * @param cells Conjunto de células vivas do tipo Table.
    * @param w Largura do tabuleiro.
    * @param h Altura do tabuleiro.
    */
  private def updateLiveCells(cells: Table, w: Int, h: Int) = {
    vivas = Nil
    for(h <- 0 until h){
      for(w <- 0 until w){
        val cell = cells.elements(h)(w)
        if(cell.alive || cell.afterLife)
        vivas = new LiveCell(new Vector2(w * squareSide, h * squareSide), cell.copy)::vivas
      }
    }
  }

  /**
    * Calcula o padding necessário para centralizar o tabuleiro.
    * @param w Largura do tabuleiro.
    * @param h Altura do tabuleiro.
    */
  def calculatePadding(w: Int, h: Int): Unit = {
    val sqrSideW = scrW / w
    val sqrSideH = (scrH - menuH) / h
    if (sqrSideW < minSquareSide || sqrSideH < minSquareSide)
    throw new Exception("Este tamanho de tabuleiro não cabe na tela")
    if (sqrSideW < sqrSideH) {
      squareSide = sqrSideW
    }
    else {
      squareSide = sqrSideH
    }
    paddingW = ((scrW - squareSide * w) / 2).toInt
    paddingH = ((scrH - menuH - squareSide * h) / 2).toInt
  }
  //==//

  //Geração de botões
  /**
    * Gera uma lista de botões do menu inferior.
    * @return Lista de botões do menu inferior.
    */
  private def createButtons: List[GameButton] = {
    var list: List[GameButton] = Nil
    //Sair
    list = new GameButton("Exit", _ => GameController.endGame())::list
    //Menu Popup com as Regras
    list = new MenuButton(GameController.getGameModeName)::list
    //Botão de limpar o tabuleiro
    list = new GameButton("Clear", _ => GameController.killAll())::list
    //Cria botão que exibe e reseta a velocidade
    //Botão de acelerar o jogo
    list = new GameButton("Speed Up", _ => GameController.speedUp(10))::list
    //Botão de reset da velocidade
    list = speedDisplay::list
    //Botão de desacelerar o jogo
    list = new GameButton("Speed Down", _ => GameController.speedDown(10))::list
    //Botão de refazer geração gravada sem calcular
    list = new GameButton("Redo", _ => GameController.redoGeneration())::list
    //Botão de calcular nova geração
    list = new GameButton("Next Gen", _ => GameController.nextGeneration())::list
    //Botão de pausar ou continuar mudança automática de geração
    list = new PlayButton("Start/Pause", b => {
      GameController.changeAutoGenState()
    })::list
    //Botão de voltar a geração gravada
    list = new GameButton("Prev Gen", _ => GameController.previousGeneration())::list
    list
  }

  /**
    * Arruma os botões do menu na posição certa.
    */
  private def arrangeButtons(): Unit ={
    val buttonPadSides = (menuW - (buttons.size*buttonW + (buttons.size-1)*buttonPad))/2
    val buttonPadTopBot = (menuH - buttonH)/2
    for(i <- buttons.indices){
      val gameButton = buttons(i)
      gameButton.setPosition1(buttonPadSides + i*(buttonW+buttonPad), buttonPadTopBot)
      gameButton.setPosition2(buttonPadSides + i*(buttonW+buttonPad)+buttonW, buttonPadTopBot+buttonH)
      gameButton match{
        case m: MenuButton =>
          m.setBLCorner(10, menuH+10)
          m.setTRCorner(scrW-10, scrH-10)
        case _ =>
      }
    }
  }
  //==//

  /**
    * Muda o estado de um botão para cor de destaque.
    * @param button índice do botão a ser mudado.
    * @param on Ligado(true) ou desligado(false)
    */
  def highlight(button: Int, on: Boolean): Unit = buttons(button).setHighlight(on)

  //Cuida do estado de andamento automático das gerações.
  /**
    * Muda o estado de andamento automático das gerações.
    */
  def changeAutoGenState(): Unit = {
    if(!menuOpen){
      paused = !paused
      buttons.foreach {
        case p: PlayButton => p.changeState()
        case _ =>
      }
    }
  }

  /**
    * Muda o estado de andamento automático das gerações para pausado.
    */
  def pauseGame(): Unit = {
    if(!paused)
      changeAutoGenState()
  }
  //==//

  //Cuida da velocidade de mudança de cada geração
  /**
    * Acelera subtraindo o multiplicador do tempo de espera entre gerações.
    * @param multiplier valor a subtrair do tempo de espera.
    */
  def speedUp(multiplier: Double): Unit ={
    if(multiplier > 1){
      if(delay-multiplier >= 10)
        delay = (delay-multiplier).toInt
      else
        delay = 10
    }
    speedButtonUpdate()
  }

  /**
    * Desacelera somando multiplicador do tempo de espera entre gerações.
    * @param multiplier valor a somar do tempo de espera.
    */
  def speedDown(multiplier: Double): Unit ={
    if(multiplier > 1){
      if(delay+multiplier <= 2000)
        delay = (delay+multiplier).toInt
      else
        delay = 2000
    }
    speedButtonUpdate()
  }

  /**
    * Muda o tempo de espera para o default.
    */
  def speedReset(): Unit ={
    delay = defaultDelay
    speedButtonUpdate()
  }

  /**
    * Atualiza a velocidade exibida no botão
    */
  def speedButtonUpdate(): Unit ={
    val speed = defaultDelay/delay.toDouble
    speedDisplay.setName(f"Speed: $speed%.2f")
  }
  //==//
}
