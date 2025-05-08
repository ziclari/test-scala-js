import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

object Main {
  var felicidad = 0
  val imagenGato = div( cls := "gato")
  def main(args: Array[String]): Unit = {
    val mensaje = div(
      cls := "gato-contenedor",

      h1("Bienvenido a La Vecindad de los Secretos"),
      p("Explora los misterios de la vecindad..."),
      imagenGato,

      button(
        "🐟 Dar comida",
        cls := "boton-comida",
        onClick --> { _ =>
          felicidad += 5
          cambiarImagen("gato-comiendo")
          actualizarEstado("¡El gato disfruta su comida! 😺")
      }),
      button(
        "🎾 Jugar", 
        cls := "boton-jugar",
        onClick --> { _ =>
          felicidad += 3
          cambiarImagen("gato-jugando")
          actualizarEstado("¡El gato corre tras la pelota! 🏃‍♂️😸")
      }),
      button(
        "💤 Dormir",
        cls := "boton-dormir",
        onClick --> { _ =>
          felicidad += 2
          cambiarImagen("gato-durmiendo")
          actualizarEstado("El gato duerme plácidamente. 😽")
      }),

      p(child.text <-- Signal.fromValue(s"Nivel de felicidad: $felicidad")),
      p(idAttr := "estadoGato")

    )

    renderOnDomContentLoaded(
      dom.document.getElementById("app"),
      mensaje
    )
  }

  def cambiarImagen(nuevaImagen: String): Unit = {
    imagenGato.ref.setAttribute("class", nuevaImagen)
  }
  def actualizarEstado(texto: String): Unit = {
    dom.document.getElementById("estadoGato").textContent = texto
  }
}

