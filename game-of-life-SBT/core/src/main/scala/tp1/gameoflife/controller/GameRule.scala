package tp1.gameoflife.controller

import tp1.gameoflife.gameengine.GameEngine

class GameRule(_name: String, gameEngine: GameEngine){
  def name: String = _name
  def getRule: GameEngine = gameEngine
}
