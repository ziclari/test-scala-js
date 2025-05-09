import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

object Main {

  val felicidad = Var(0)
  val ropaActual = Var("assets/cow1.png")
  val filtroActual = Var("none") // CSS filter

  val clothesOptions = List("cheetah1.png", "cow1.png", "flame1.png", "hair1.png", "tiger1.png")
  val filterOptions = List(
    "none" -> "Sin filtro",
    "grayscale(100%)" -> "Blanco y negro",
    "sepia(100%)" -> "Sepia",
    "invert(100%)" -> "Invertir colores",
    "hue-rotate(90deg)" -> "Colores 90°"
  )

  def main(args: Array[String]): Unit = {
    val app = div(
      cls := "gato-contenedor",
      h1("Bienvenido a La Vecindad de los Secretos"),
      p("Explora los misterios de la vecindad..."),

      // Contenedor de imágenes
      div(
        idAttr := "container",
        img(
          src := "assets/base.png",
          cls := "layer"
        ),
        img(
          cls := "layer",
          src <-- ropaActual.signal,
          styleAttr <-- filtroActual.signal.map(f => s"filter: $f")
        )
      ),

      // Botones de ropa
      div(
        idAttr := "buttons",
        clothesOptions.map { filename =>
          button(
            filename.stripSuffix(".png").capitalize,
            onClick --> (_ => ropaActual.set(s"assets/$filename"))
          )
        }
      ),

      // Botones de acción (alimentar, jugar, dormir)
      div(
        cls := "boton-contenedor",
        button(
          "🐟 Dar comida",
          onClick --> { _ =>
            felicidad.update(_ + 5)
            actualizarEstado("¡El gato disfruta su comida! 😺")
          }
        ),
        button(
          "🎾 Jugar",
          onClick --> { _ =>
            felicidad.update(_ + 3)
            actualizarEstado("¡El gato corre tras la pelota! 🏃‍♂️😸")
          }
        ),
        button(
          "💤 Dormir",
          onClick --> { _ =>
            felicidad.update(_ + 2)
            actualizarEstado("El gato duerme plácidamente. 😽")
          }
        )
      ),

      // Filtros
      div(
        h3("Cambiar filtro de color:"),
        filterOptions.map { case (filtro, nombre) =>
          button(
            nombre,
            onClick --> (_ => filtroActual.set(filtro))
          )
        }
      ),

      // Estado y felicidad
      p(child.text <-- felicidad.signal.map(f => s"Nivel de felicidad: $f")),
      p(idAttr := "estadoGato")
    )

    renderOnDomContentLoaded(
      dom.document.getElementById("app"),
      app
    )
  }

  def actualizarEstado(texto: String): Unit = {
    dom.document.getElementById("estadoGato").textContent = texto
  }
}
