package tp1.gameoflife.gameengine

class Table (height: Int, width: Int) {

  var elements:Array[Array[Cell]] = Array.ofDim[Cell](height, width)

  def clean(): Unit = {

    for (h <- 0 until height) {
      for (w <- 0 until width) {

        elements(h)(w).alive = false

      }
    }

  }

}
