import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

object Main {

  val felicidad = Var(0)
  val ropaActual = Var("assets/cow1.png")
  val clothesOptions = List("cheetah1.png", "cow1.png", "flame1.png", "hair1.png", "tiger1.png")
  
  // Filtros independientes para base y ropa
  val filtroBase = Var("none")
  val filtroRopa = Var("none")

  // Sliders individuales
  val brilloBase = Var(100)
  val contrasteBase = Var(100)
  val hueBase = Var(0)

  val brilloRopa = Var(100)
  val contrasteRopa = Var(100)
  val hueRopa = Var(0)

  def main(args: Array[String]): Unit = {
    // Función para construir la cadena de filtro
    def cssFilter(brillo: Int, contraste: Int, hue: Int): String =
      s"brightness(${brillo}%) contrast(${contraste}%) hue-rotate(${hue}deg)"

    // Actualizar filtros al combinar sliders
    filtroBase <-- combineLatest3(brilloBase.signal, contrasteBase.signal, hueBase.signal)
      .map(cssFilter)

    filtroRopa <-- combineLatest3(brilloRopa.signal, contrasteRopa.signal, hueRopa.signal)
      .map(cssFilter)

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

      // Selección de ropa
      h3("Selecciona un estilo:"),
      div(
        clothesOptions.map { filename =>
          button(
            filename.stripSuffix(".png").capitalize,
            onClick --> (_ => ropaActual.set(s"assets/$filename"))
          )
        }
      ),
      // Filtros base
      h3("Filtro para imagen base"),
      filtroControls(brilloBase, contrasteBase, hueBase),

      // Filtros ropa
      h3("Filtro para ropa"),
      filtroControls(brilloRopa, contrasteRopa, hueRopa),

      // Acciones
      h3("Acciones del gato"),
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
  def filtroControls(brillo: Var[Int], contraste: Var[Int], hue: Var[Int]): HtmlElement = {
    div(
      label("Brillo:"),
      input(
        typ := "range",
        min := "50", max := "200", value := "100",
        onInput.mapToValue.map(_.toInt) --> brillo
      ),
      label("Contraste:"),
      input(
        typ := "range",
        min := "50", max := "200", value := "100",
        onInput.mapToValue.map(_.toInt) --> contraste
      ),
      label("Tono (Hue):"),
      input(
        typ := "range",
        min := "0", max := "360", value := "0",
        onInput.mapToValue.map(_.toInt) --> hue
      )
    )
  }
}
