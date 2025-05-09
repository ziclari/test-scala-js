enablePlugins(ScalaJSPlugin)

Compile / resourceGenerators += Def.task {
  val htmlFile = baseDirectory.value / "resources" / "index.html"
  val targetDir = baseDirectory.value / "target" / "scala-3.3.3"
  
  IO.createDirectory(targetDir) // Asegura que el directorio exista
  IO.copyFile(htmlFile, targetDir / "index.html")
  
  Seq(targetDir / "index.html")
}

Compile / resourceGenerators += Def.task {
  val resDir = baseDirectory.value / "resources"
  val targetDir = baseDirectory.value / "target" / "scala-3.3.3"

  IO.createDirectory(targetDir)
  IO.copyFile(resDir / "index.html", targetDir / "index.html")

  // Copiar carpeta de imágenes
  val assetsDir = resDir / "assets"
  val targetAssetsDir = targetDir / "assets"
  IO.copyDirectory(assetsDir, targetAssetsDir)

  Seq(targetDir / "index.html")
}

libraryDependencies += "com.raquo" %%% "laminar" % "17.0.0"

scalaVersion := "3.3.3"
name := "VecindadDeLosSecretos"
version := "0.1.0"

scalaJSUseMainModuleInitializer := true