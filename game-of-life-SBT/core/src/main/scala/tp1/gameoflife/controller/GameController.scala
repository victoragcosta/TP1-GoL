package tp1.gameoflife.controller

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.{Game, Gdx}
import tp1.gameoflife.gameengine.{GameEngine, Statistics}
import tp1.gameoflife.view.GameView

object GameController extends Game {

  private var ruleNumber: Int = 0
  private var gameMode: GameEngine = DependencyInjector.getRule(ruleNumber)

  def getGameModeName: String = gameMode.toString

  //Controle de Andamento do Jogo
  /**
    * Início do Programa
    * Dá setup na tela, inputs. Calcula o padding para centralizar o tabuleiro.
    * Gera o primeiro tabuleiro na View.
    */
  override def create() {
    while(GameView == null || GameView.screen == null){}
    this.setScreen(GameView.screen)
    Gdx.input.setInputProcessor(new GameInputHandler)
    GameView.calculatePadding(gameMode.width, gameMode.height)
    GameView.update(gameMode.currentGeneration, gameMode.width, gameMode.height)
  }
  def changeAutoGenState(): Unit = {
    GameView.changeAutoGenState()
  }
  def pauseGame(): Unit = GameView.pauseGame()
  def endGame(): Unit ={
    Statistics.displayStatistics()
    Gdx.app.exit()
  }
  //==//

  //Controle de Gerações
  /**
    * Próxima Geração
    * Calcula a próxima geração.
    * Gera o novo tabuleiro na View.
    */
  def nextGeneration(): Unit = {
    try{
      gameMode.nextGeneration()
      GameView.update(gameMode.currentGeneration, gameMode.width, gameMode.height)
    } catch {
      case _: Exception =>
    }
  }

  /**
    * Geração Anterior
    * Tenta mudar a geração atual para uma gravada e dá update no no tabuleiro da View.
    */
  def previousGeneration(): Unit = {
    try{
      gameMode.undo()
      GameView.update(gameMode.currentGeneration, gameMode.width, gameMode.height)
    } catch {
      case _: Exception =>
    }
  }

  /**
    * Refazer Geração
    * Tenta mudar a geração atual para uma gravada a frente e dá update no no tabuleiro da View.
    */
  def redoGeneration(): Unit = {
    try{
      gameMode.redo()
      GameView.update(gameMode.currentGeneration, gameMode.width, gameMode.height)
    } catch {
      case _: Exception =>
    }
  }
  //==//

  //Controle de Células
  /**
    * Revive Célula
    *
    * Muda ou mantém o estado de uma célula (x,y) para viva.
    * Filtra a entrada para (x,y) só executar se estiver dentro do tabuleiro.
    * @param x Coordenada x da célula no tabuleiro virtual.
    * @param y Coordenada y da célula no tabuleiro virtual.
    */
  def makeAlive(x: Int, y: Int): Unit ={
    try{
      if(inBounds(x,y))
        gameMode.reviveCell(y,x)
      GameView.update(gameMode.currentGeneration, gameMode.width, gameMode.height)
    } catch {
      case _: Exception =>
    }
  }

  /**
    * Mata Célula
    *
    * Muda ou mantém o estado de uma célula (x,y) para morta.
    * Filtra a entrada para (x,y) só executar se estiver dentro do tabuleiro.
    * @param x Coordenada x da célula no tabuleiro virtual.
    * @param y Coordenada y da célula no tabuleiro virtual.
    */
  def killCell(x: Int, y: Int): Unit ={
    try{
      if(inBounds(x,y)){
        gameMode.killCell(y,x)
        Statistics.addDeath()
      }
      GameView.update(gameMode.currentGeneration, gameMode.width, gameMode.height)
    } catch {
      case _: Exception =>
    }
  }

  /**
    * Mata Todas as Células
    *
    * Muda ou mantém o estado de todas as células para mortas.
    */
  def killAll(): Unit = {
    gameMode.currentGeneration.clean()
    GameView.update(gameMode.currentGeneration, gameMode.width, gameMode.height)
  }

  /**
    * Muda Cor
    * Muda a cor de uma célula (x,y).
    * Filtra a entrada para (x,y) só executar se estiver dentro do tabuleiro.
    * @param x Coordenada x da célula no tabuleiro virtual.
    * @param y Coordenada y da célula no tabuleiro virtual.
    * @return Cor nova da célula.
    */
  def switchColor(x: Int, y: Int): Color = {
    try{
      if(inBounds(x,y))
        gameMode.switchColor(y,x)
      gameMode.defaultColor
    } catch {
      case _: Exception =>
        gameMode.defaultColor
    }
  }

  /**
    * Célula Está Viva
    *
    * Testa se a célula (x, y) está viva.
    * Filtra (x, y) para só aceitar aqueles que estejam no tabuleiro.
    * @param x Coordenada x da célula no tabuleiro virtual.
    * @param y Coordenada y da célula no tabuleiro virtual.
    * @return Boolean representando se a célula está viva(true) ou morta(false).
    */
  def cellIsAlive(x: Int, y: Int): Boolean = {
    try {
      inBounds(x,y) && gameMode.isCellAlive(y,x)
    } catch {
      case _: Exception =>
        false
    }
  }
  //==//

  //Controle de Regras de Game of Life
  /**
    * Muda Regra
    *
    * Faz o ciclo entre as regras.
    */
  def changeRule(): Unit = {
    ruleNumber+=1
    if(ruleNumber >= DependencyInjector.classes.length){
      ruleNumber = 0
    }
    DependencyInjector.getRule(ruleNumber).currentGeneration = gameMode.currentGeneration
    gameMode = DependencyInjector.getRule(ruleNumber)
    gameMode.resetColors()
    GameView.update(gameMode.currentGeneration, gameMode.width, gameMode.height)
  }

  /**
    * Muda Regra
    *
    * Muda para a instância de regra passada.
    * @param gameRule Instância da regra.
    */
  def changeRule(gameRule: GameEngine): Unit = {
    try{
      gameRule.currentGeneration = gameMode.currentGeneration
      gameMode = gameRule
      gameMode.resetColors()
      GameView.update(gameMode.currentGeneration, gameMode.width, gameMode.height)
    } catch {
      case e: Exception => println(e.getMessage)
    }
  }
  //==//

  //Auxiliar
  /**
    * Dentro do Tabuleiro
    *
    * Testa se está dentro do tabuleiro a célula (x,y).
    * @param x Coordenada x da célula no tabuleiro virtual.
    * @param y Coordenada y da célula no tabuleiro virtual.
    * @return Boolean onde true é está dentro do tabuleiro.
    */
  private def inBounds(x: Int,y: Int): Boolean = 0 <= x && x < gameMode.width && 0 <= y && y < gameMode.height
  //==//

  //Controle de velocidade
  /**
    * Acelera
    *
    * Subtrai um valor ao tempo de auto atualização da geração.
    * @param multiplier Valor a subtrair em milisegundos.
    */
  def speedUp(multiplier: Double): Unit = GameView.speedUp(multiplier)

  /**
    * Desacelera
    *
    * Soma um valor ao tempo de auto atualização da geração.
    * @param multiplier Valor a somar em milisegundos.
    */
  def speedDown(multiplier: Double): Unit = GameView.speedDown(multiplier)

  /**
    * Resetar
    *
    * Muda o tempo de auto atualização para o padrão. Nesse projeto 100 milisegundos.
    */
  def speedReset(): Unit = GameView.speedReset()
  //==//

}
