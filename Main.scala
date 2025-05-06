import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom
@main
object Main {
  def main(args: Array[String]): Unit = {
    renderOnDomContentLoaded(
      dom.document.getElementById("app"),
      div(
        h1("Bienvenido a La Vecindad de los Secretos"),
        p("Explora los misterios de la vecindad..."),
        button("Descubrir", onClick --> (_ => println("¡Evento activado!")))
      )
    )
  }
}

