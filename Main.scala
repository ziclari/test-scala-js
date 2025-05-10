import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.api.features.unitArrows.*
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

    // Filtros combinados
brilloBase.signal
  .combineWithFn(contrasteBase.signal)((b, c) => (b, c))
  .combineWithFn(hueBase.signal)((bc, h) => {
    val (b, c) = bc
    cssFilter(b, c, h)
  }) --> filtroBase

brilloRopa.signal
  .combineWithFn(contrasteRopa.signal)((b, c) => (b, c))
  .combineWithFn(hueRopa.signal)((bc, h) => {
    val (b, c) = bc
    cssFilter(b, c, h)
  }) --> filtroRopa

    val app = div(
      cls := "gato-contenedor",
      h1("Bienvenido a La Vecindad de los Secretos"),
      p("Explora los misterios de la vecindad..."),

      // Contenedor de imágenes
      div(
        idAttr := "container",
        img(
          cls := "layer",
          src := "assets/base.png",
          style <-- filtroBase.signal.map(f => s"filter: $f")
        ),
        img(
          cls := "layer",
          src <-- ropaActual.signal,
          style <-- filtroRopa.signal.map(f => s"filter: $f")
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
        minAttr := "50",
        maxAttr := "200",
        value := "100",
        onInput.mapToValue.map(_.toInt) --> brillo
      ),

      label("Contraste:"),
      input(
        typ := "range",
        minAttr := "50",
        maxAttr := "200",
        value := "100",
        onInput.mapToValue.map(_.toInt) --> contraste
      ),

      label("Tono (Hue):"),
      input(
        typ := "range",
        minAttr := "0",
        maxAttr := "360",
        value := "0",
        onInput.mapToValue.map(_.toInt) --> hue
      )
    )
  }
}
