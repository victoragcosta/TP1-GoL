package tp1.gameoflife.pkg

import com.badlogic.gdx.backends.lwjgl._
import tp1.gameoflife.controller.Controller

object Main{
  def main(args: Array[String]): Unit = {
    val cfg = new LwjglApplicationConfiguration
    cfg.title = "Game Of Life"
    cfg.height = 480
    cfg.width = 800
    cfg.forceExit = false
    new LwjglApplication(Controller, cfg)
  }
}
