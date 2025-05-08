import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

object Main {
  val felicidad = Var(0) 
  val imagenGato = div( cls := "gato")
  def main(args: Array[String]): Unit = {
    val mensaje = div(
      cls := "gato-contenedor",

      h1("Bienvenido a La Vecindad de los Secretos"),
      p("Explora los misterios de la vecindad..."),
      imagenGato,

      div(
        cls := "boton-contenedor",
        button(
          "🐟 Dar comida",
          cls := "boton-comida",
          onClick --> { _ =>
            felicidad.update(_ + 5) 
            cambiarImagen("gato-comiendo")
            actualizarEstado("¡El gato disfruta su comida! 😺")
        }),
        button(
          "🎾 Jugar", 
          cls := "boton-jugar",
          onClick --> { _ =>
            felicidad.update(_ + 3)
            cambiarImagen("gato-jugando")
            actualizarEstado("¡El gato corre tras la pelota! 🏃‍♂️😸")
        }),
        button(
          "💤 Dormir",
          cls := "boton-dormir",
          onClick --> { _ =>
            felicidad.update(_ + 2)
            cambiarImagen("gato-durmiendo")
            actualizarEstado("El gato duerme plácidamente. 😽")
        })
      ),

      p(child.text <-- felicidad.signal.map(f => s"Nivel de felicidad: $f")),
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

