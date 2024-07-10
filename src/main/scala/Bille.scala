import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

final case class Bille(x: Double, y: Double, dx: Double, dy: Double, color: Color) {
  val circle = new Circle {
    centerX = x
    centerY = y
    radius = 10
    fill = color
  }

  def move(screenWidth: Double, screenHeight: Double): Bille = {
    val newDx = if (x < 0 || x > screenWidth) -dx else dx
    val newDy = if (y < 0 || y > screenHeight) -dy else dy
    val newX = x + newDx
    val newY = y + newDy
    this.copy(x = newX, y = newY, dx = newDx, dy = newDy)
  }

  def collidesWith(other: Bille): Boolean = {
    val dx = other.x - x
    val dy = other.y - y
    val distance = Math.sqrt(dx * dx + dy * dy)
    distance < circle.radius.value + other.circle.radius.value
  }

  def handleCollision(other: Bille): Bille = {
    if (collidesWith(other)) {
      val dx = other.x - x
      val dy = other.y - y
      val angle = Math.atan2(dy, dx)
      val newDx = -Math.cos(angle)
      val newDy = -Math.sin(angle)
      this.copy(dx = newDx, dy = newDy)
    } else {
      this
    }
  }
}