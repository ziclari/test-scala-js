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
        cambiarImagen("gato-comiendo")
        actualizarEstado("¡El gato disfruta su comida! 😺")
        cls := "boton-comida"
      }),
      button("🎾 Jugar", onClick --> { _ =>
        felicidad += 3
        cambiarImagen("gato-jugando")
        actualizarEstado("¡El gato corre tras la pelota! 🏃‍♂️😸")
        cls := "boton-jugar"
      }),
      button("💤 Dormir", onClick --> { _ =>
        felicidad += 2
        cambiarImagen("gato-durmiendo")
        actualizarEstado("El gato duerme plácidamente. 😽")
        cls := "boton-dormir"
      }),
      p(child.text <-- Signal.fromValue(s"Nivel de felicidad: $felicidad")),
      p(idAttr := "estadoGato")

      cls := "gato-contenedor"
    )

    renderOnDomContentLoaded(
      dom.document.getElementById("app"),
      mensaje
    )
  }
  def cambiarImagen(nuevaImagen: String): Unit = {
    imagenGato.ref.setAttribute("class", s"$nuevaImagen")
  }
  def actualizarEstado(texto: String): Unit = {
    dom.document.getElementById("estadoGato").textContent = texto
  }
}

