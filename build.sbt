enablePlugins(ScalaJSPlugin)

Compile / resourceGenerators += Def.task {
  val htmlFile = baseDirectory.value / "resources" / "index.html"
  val targetDir = (Compile / resourceManaged).value
  IO.copyFile(htmlFile, targetDir / "index.html")
  Seq(targetDir / "index.html")
}

libraryDependencies += "com.raquo" %%% "laminar" % "17.0.0"

scalaVersion := "3.3.3"
name := "VecindadDeLosSecretos"
version := "0.1.0"

scalaJSUseMainModuleInitializer := true