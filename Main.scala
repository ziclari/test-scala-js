import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

object Main {
  var felicidad = 0
  val imagenGato = div( cls := "gato")
  def main(args: Array[String]): Unit = {
    val mensaje = div(
      h1("Bienvenido a La Vecindad de los Secretos"),
      p("Explora los misterios de la vecindad..."),
      imagenGato,
      button("🐟 Dar comida", onClick --> { _ =>
        felicidad += 5
        cambiarImagen("gato_comiendo")
        actualizarEstado("¡El gato disfruta su comida! 😺")
      }),
      button("🎾 Jugar", onClick --> { _ =>
        felicidad += 3
        cambiarImagen("gato_jugando")
        actualizarEstado("¡El gato corre tras la pelota! 🏃‍♂️😸")
      }),
      button("💤 Dormir", onClick --> { _ =>
        felicidad += 2
        cambiarImagen("gato_durmiendo")
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
    imagenGato.ref.setAttribute("class", s"gato $nuevaClase")
  }
  def actualizarEstado(texto: String): Unit = {
    dom.document.getElementById("estadoGato").textContent = texto
  }
}

