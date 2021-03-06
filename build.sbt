name := "scala-epub"

version := "0.1.1"

scalaVersion := "2.10.5"

libraryDependencies ++= Seq(
    "org.specs2" %% "specs2" % "1.14",
    "org.specs2" %% "specs2-scalaz-core" % "6.0.4" % "test")

resolvers ++= Seq("snapshots" at "http://scala-tools.org/repo-snapshots",
                  "releases"  at "http://scala-tools.org/repo-releases")

