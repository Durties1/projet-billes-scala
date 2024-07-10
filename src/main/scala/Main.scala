import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.animation.AnimationTimer

import scala.util.Random
import scalafx.scene.paint.Color
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.stage.Screen

object Main extends JFXApp3 {


  val random = new Random()


  override def start(): Unit = {
    val (screenWidth, screenHeight) = (Screen.primary.visualBounds.width, Screen.primary.visualBounds.height)
    var billes = for (_ <- 1 to 50) yield {
      Bille(
        random.nextDouble() * screenWidth,
        random.nextDouble() * screenHeight,
        random.nextDouble() * 4 - 2,
        random.nextDouble() * 4 - 2,
        Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))
      )
    }

    val valScene = new Scene {
      content = billes.map(_.circle)
    }
    
    stage = new PrimaryStage {
      title = "Billes"
      width = screenWidth
      height = screenHeight
      scene = valScene
    }

    val timer = AnimationTimer(_ => {
      val newBilles = for (bille <- billes) yield {
        var newBille = bille.move(screenWidth, screenHeight)
        for (otherBille <- billes if otherBille != bille) {
          newBille = newBille.handleCollision(otherBille)
        }
        newBille
      }
      billes = newBilles
      valScene.content = billes.map(_.circle)
    })

    timer.start()
  }
}