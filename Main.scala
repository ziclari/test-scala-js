import com.raquo.laminar.api.L.{*, given}

object Main {
  def main(): Unit = {
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

