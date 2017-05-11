import com.badlogic.gdx.backends.lwjgl._
import tp1.gameoflife.controller.GameController

object Main{
  def main(args: Array[String]): Unit = {
    val cfg = new LwjglApplicationConfiguration
    cfg.title = "Game Of Life"
    cfg.height = 580
    cfg.width = 800
    cfg.forceExit = false
    new LwjglApplication(GameController, cfg)
  }
}
