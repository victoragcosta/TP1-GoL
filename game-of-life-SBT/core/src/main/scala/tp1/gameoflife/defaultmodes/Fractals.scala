package tp1.gameoflife.defaultmodes

import com.badlogic.gdx.graphics.Color
import tp1.gameoflife.gameengine.GameEngine
//import scala.collection.mutable.MutableList

class StarWars (override val height: Int, override val width: Int) extends GameEngine{

  override def toString: String = "StarWars"

  override val description: String = "The 6 at the end means that if a Star Wars particle dies, it stays for 6 frames, and dissapears, instead of going away instantly. The 'dying' particle is not counted as a neighbor, and nothing can be born into that space until it dies. The dying particle will also turn from a dark blue to a much brighter blue as it dies. "

  override def shouldKeepAlive(cellHeight: Int, cellWidth: Int): Boolean = true

  override val defaultColor = new Color(0f, 0f, 0f, 1)

  override def shouldRevive(cellHeight: Int, cellWidth: Int): Boolean = false

}
