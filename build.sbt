enablePlugins(ScalaJSPlugin)

Compile / unmanagedResourceDirectories += baseDirectory.value / "resources"

libraryDependencies += "com.raquo" %%% "laminar" % "17.0.0"

scalaVersion := "3.3.3"
name := "VecindadDeLosSecretos"
version := "0.1.0"

scalaJSUseMainModuleInitializer := true