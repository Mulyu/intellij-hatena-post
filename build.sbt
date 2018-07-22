lazy val hatenaPost =
  (project in file("."))
    .enablePlugins(SbtIdeaPlugin)
    .settings(
      libraryDependencies ++= Seq(
        "org.scalaj" %% "scalaj-http" % "2.4.0"
      )
    )

